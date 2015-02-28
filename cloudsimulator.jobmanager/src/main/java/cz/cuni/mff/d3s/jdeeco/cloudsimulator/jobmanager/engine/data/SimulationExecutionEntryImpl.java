package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.data;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.joda.time.DateTime;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.models.SimulationExecution;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.models.SimulationRun;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.SimulationStatus;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.updates.SimulationStatusUpdate;

public class SimulationExecutionEntryImpl implements SimulationExecutionEntry {

	private final HashMap<Integer, SimulationRunEntry> completedRuns = new HashMap<Integer, SimulationRunEntry>();
	private final HashMap<Integer, SimulationRunEntry> startedRuns = new HashMap<Integer, SimulationRunEntry>();
	private final HashMap<Integer, SimulationRunEntry> notStartedRuns = new HashMap<Integer, SimulationRunEntry>();
	private final HashMap<Integer, SimulationRunEntry> errorRuns = new HashMap<Integer, SimulationRunEntry>();

	private final SimulationExecution data;
	private final SimulationExecutionEntryListener listener;
	private final SimulationExecutionStatistics statistics;

	private final ExecutionDeadlineSettings deadlineSettings;

	private String packageName;

	private boolean repeatedlyThrowsError = false;
	private boolean started = false;
	private boolean stopped = false;

	public SimulationExecutionEntryImpl(SimulationExecution data, SimulationExecutionEntryListener listener,
			SimulationExecutionStatistics statistics, SimulationRunEntryFactory simulationRunEntryFactory) {
		this.data = data;
		this.listener = listener;
		this.statistics = statistics;

		this.deadlineSettings = new ExecutionDeadlineSettings(data.getEndSpecificationType(), new DateTime(data));

		for (SimulationRun simulationRun : data.getSimulationRuns()) {
			SimulationRunEntry newEntry = simulationRunEntryFactory.create(simulationRun, this);
			notStartedRuns.put(simulationRun.getId(), newEntry);
		}
	}

	@Override
	public int getId() {
		return this.data.getId();
	}

	@Override
	public SimulationStatus getStatus() {
		if (stopped)
			return SimulationStatus.Stopped;

		if (repeatedlyThrowsError) {
			return SimulationStatus.ErrorOccured;
		} else if (startedRuns.isEmpty() && completedRuns.isEmpty()) {
			return SimulationStatus.Created;
		} else if (!startedRuns.isEmpty() || notStartedRuns.isEmpty()) {
			return SimulationStatus.Started;
		} else {
			return SimulationStatus.Completed;
		}
	}

	@Override
	public int getNotStartedRunsCount() {
		return notStartedRuns.size();
	}

	@Override
	public List<SimulationRunEntry> getNotStartedRuns() {
		return notStartedRuns.values().stream().collect(Collectors.toList());
	}

	@Override
	public boolean containsSimulationRun(int simulationRunId) {
		return notStartedRuns.containsKey(simulationRunId) || startedRuns.containsKey(simulationRunId)
				|| completedRuns.containsKey(simulationRunId) || errorRuns.containsKey(simulationRunId);
	}

	@Override
	public void startSimulationRun(SimulationRunEntry toStartEntry) {
		if (stopped)
			return;

		if (!started) {
			listener.executionStarted(this);
			started = true;
		}

		int entryId = toStartEntry.getId();
		notStartedRuns.remove(entryId);
		toStartEntry.setStatus(SimulationStatus.Started);
		startedRuns.put(entryId, toStartEntry);
		statistics.simulationStarted(entryId);
	}

	@Override
	public void updateRunStatus(SimulationStatusUpdate update) {
		if (stopped)
			return;

		int simulationRunId = update.getSimulationRunId();

		SimulationRunEntry simulationRunEntry;
		if (startedRuns.containsKey(simulationRunId)) {
			simulationRunEntry = startedRuns.remove(simulationRunId);
		} else if (errorRuns.containsKey(simulationRunId)) {
			simulationRunEntry = errorRuns.remove(simulationRunId);
		} else {
			throw new RuntimeException("Cannot find simulation run entry with id: " + simulationRunId);
		}

		SimulationStatus simulationStatus = update.getSimulationStatus();
		simulationRunEntry.setStatus(simulationStatus);

		switch (simulationStatus) {
		case Stopped:
			completedRuns.put(simulationRunId, simulationRunEntry);
			break;
		case Completed:
			completedRuns.put(simulationRunId, simulationRunEntry);
			statistics.simulationCompleted(simulationRunId);
			break;
		case ErrorOccured:
			errorRuns.put(simulationRunId, simulationRunEntry);
			break;

		case Started:
		case None:
		case Created:
		default:
			throw new RuntimeException(String.format("Cannot update simulation status to '%s'.", simulationStatus));
		}
		
		// notify completion
		if (getStatus() == SimulationStatus.Completed) {
			listener.executionCompleted(this);
		}
	}

	@Override
	public SimulationExecutionStatistics getStatistics() {
		return statistics;
	}

	@Override
	public ExecutionDeadlineSettings getDeadlineSettings() {
		return deadlineSettings;
	}

	@Override
	public String getPackageName() {
		return packageName;
	}

	@Override
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	@Override
	public void stop() {
		this.stopped = true;

		stopRuns(notStartedRuns);
		stopRuns(startedRuns);
	}

	private void stopRuns(HashMap<Integer, SimulationRunEntry> runs) {
		runs.forEach((key, value) -> {
			value.setStatus(SimulationStatus.Stopped);
			completedRuns.put(key, value);
		});
		runs.clear();
	}
}
