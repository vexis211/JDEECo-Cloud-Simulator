package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.data;

import java.util.HashMap;
import java.util.Optional;

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

	private final Object locker = new Object();

	private final SimulationExecution data;
	private final SimulationExecutionStatistics statistics;

	private final ExecutionDeadlineSettings deadlineSettings;

	private boolean repeatedlyThrowsError = false;

	public SimulationExecutionEntryImpl(SimulationExecution data, SimulationExecutionStatistics statistics,
			SimulationRunEntryFactory simulationRunEntryFactory) {
		this.data = data;
		this.statistics = statistics;

		this.deadlineSettings = new ExecutionDeadlineSettings(data.getEndSpecificationType(), new DateTime(data));

		for (SimulationRun simulationRun : data.getSimulationRuns()) {
			SimulationRunEntry newEntry = simulationRunEntryFactory.create(simulationRun);
			notStartedRuns.put(simulationRun.getId(), newEntry);
		}
	}

	@Override
	public int getId() {
		return this.data.getId();
	}

	@Override
	public SimulationStatus getStatus() {
		synchronized (locker) {
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
	}

	@Override
	public int getNotStartedRunsCount() {
		synchronized (locker) {
			return notStartedRuns.size();
		}
	}

	@Override
	public boolean containsSimulationRun(int simulationRunId) {
		synchronized (locker) {
			return notStartedRuns.containsKey(simulationRunId) || startedRuns.containsKey(simulationRunId)
					|| completedRuns.containsKey(simulationRunId) || errorRuns.containsKey(simulationRunId);
		}
	}

	@Override
	public SimulationRunEntry startSimulationRun() {
		synchronized (locker) {
			Optional<Integer> toStartIdOptional = notStartedRuns.keySet().stream().findAny();

			if (toStartIdOptional.isPresent()) {
				Integer toStartId = toStartIdOptional.get();
				SimulationRunEntry toStartEntry = notStartedRuns.remove(toStartId);
				toStartEntry.setStatus(SimulationStatus.Started);
				startedRuns.put(toStartId, toStartEntry);
				statistics.simulationStarted(toStartId);
				return toStartEntry;
			} else {
				return null;
			}
		}
	}

	@Override
	public void updateRunStatus(SimulationStatusUpdate update) {
		synchronized (locker) {
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
}
