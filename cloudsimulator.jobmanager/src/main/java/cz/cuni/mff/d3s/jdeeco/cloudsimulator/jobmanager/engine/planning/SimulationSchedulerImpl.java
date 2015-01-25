package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.planning;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.models.ExecutionEndSpecificationType;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.data.SimulationExecutionEntry;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.data.SimulationManager;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.data.SimulationRunEntry;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.workers.WorkerInstance;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.workers.WorkerManager;

public class SimulationSchedulerImpl implements SimulationScheduler {

	private final SimulationManager simulationManager;
	private final WorkerManager workerManager;
	private final SimulationSchedulerSettings settings;
	private final SimulationTimeEstimator simulationTimeEstimator;

	public SimulationSchedulerImpl(SimulationManager simulationManager, WorkerManager workerManager,
			SimulationSchedulerSettings settings, SimulationTimeEstimator simulationTimeEstimator) {
		this.simulationManager = simulationManager;
		this.workerManager = workerManager;
		this.settings = settings;
		this.simulationTimeEstimator = simulationTimeEstimator;
	}

	@Override
	public void recalculateSchedule() {

		List<WorkerInstance> preparedWorkers = workerManager.listPreparedWorkers();
		List<WorkerInstance> simulatingWorkers = workerManager.listSimulatingWorkers();

		List<SimulationExecutionEntry> allExecutions = simulationManager.listExecutions();

		List<SimulationRunEntry> toRunEntries = new ArrayList<>();
		int additionalRequestedWorkerCount = 0;

		// ----------------- FASTEST -----------------

		List<SimulationExecutionEntry> fastExecutions = filterExecutions(allExecutions,
				ExecutionEndSpecificationType.AsFastAsPossible);

		if (!fastExecutions.isEmpty()) {
			for (SimulationExecutionEntry fastEntry : fastExecutions) {
				while (preparedWorkers.size() > toRunEntries.size()) {
					SimulationRunEntry startedRun = fastEntry.startSimulationRun();
					if (startedRun != null) {
						toRunEntries.add(startedRun);
					} else {
						break;
					}
				}
			}
			if (preparedWorkers.size() == toRunEntries.size()) {
				int notStartedRunCount = fastExecutions.stream().map(x -> x.getNotStartedRunsCount())
						.reduce(Integer::sum).get();
				additionalRequestedWorkerCount += notStartedRunCount;
			}
		}

		// ----------------- BY DATE -----------------
		Comparator<SimulationExecutionEntry> comparator = (exec1, exec2) -> Long.compare(exec1.getDeadlineSettings()
				.getEndDate().getMillis(), exec2.getDeadlineSettings().getEndDate().getMillis());
		List<SimulationExecutionEntry> toDateExecutions = filterExecutions(allExecutions,
				ExecutionEndSpecificationType.ToDate).stream().sorted(comparator).collect(Collectors.toList());
		for (SimulationExecutionEntry toDateEntry : toDateExecutions) {

		}

		// ----------------- CHEAPEST -----------------

		// run cheapest only if anything is running
		if (simulatingWorkers.isEmpty() && toRunEntries.isEmpty()) {
			List<SimulationExecutionEntry> cheapExecutions = filterExecutions(allExecutions,
					ExecutionEndSpecificationType.AsCheapAsPossible);

			if (preparedWorkers.size() > 0) {
				for (SimulationExecutionEntry cheapEntry : cheapExecutions) {
					SimulationRunEntry startedRun = cheapEntry.startSimulationRun();
					// add only one - it is the cheapest
					if (startedRun != null) {
						toRunEntries.add(startedRun);
						break;
					}
				}
			} else {
				// add at least one worker
				additionalRequestedWorkerCount = Math.max(additionalRequestedWorkerCount, 1);
			}
			
		}

		// TODO
	}

	private List<SimulationExecutionEntry> filterExecutions(List<SimulationExecutionEntry> executions,
			ExecutionEndSpecificationType type) {

		return executions.stream().filter(x -> x.getDeadlineSettings().getEndSpecificationType() == type)
				.collect(Collectors.toList());
	}
}
