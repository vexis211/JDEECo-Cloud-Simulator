package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.planning;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.joda.time.DateTime;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.data.ExecutionEndSpecificationType;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.SimulationExecutionEntry;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.SimulationManager;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.SimulationRunEntry;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.workers.WorkerInstance;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.workers.WorkerManager;

public class SimulationSchedulerImpl implements SimulationScheduler {

	private final SimulationManager simulationManager;
	private final WorkerManager workerManager;
	private final SimulationSchedulerSettings settings;
	private final SimulationTimeEstimator simulationTimeEstimator;
	private final SimulationPlan simulationPlan;

	public SimulationSchedulerImpl(SimulationManager simulationManager, WorkerManager workerManager,
			SimulationSchedulerSettings settings, SimulationTimeEstimator simulationTimeEstimator,
			SimulationPlanFactory simulationPlanFactory) {
		this.simulationManager = simulationManager;
		this.workerManager = workerManager;
		this.settings = settings;
		this.simulationTimeEstimator = simulationTimeEstimator;

		this.simulationPlan = simulationPlanFactory.create();
	}

	@Override
	public SimulationPlan getSimulationPlan() {
		return simulationPlan;
	}

	@Override
	public void recalculateSchedule() {

		// update plan according to current workers
		List<WorkerInstance> allWorkers = workerManager.listAvailableWorkers();

		// if there is no started worker start one
		if (allWorkers.isEmpty()) {
			workerManager.startWorker();
			allWorkers = workerManager.listAvailableWorkers();
		}

		simulationPlan.update(allWorkers);

		List<SimulationExecutionEntry> allExecutions = simulationManager.listExecutions();
		DateTime workerTimeToStart = workerManager.whenWorkerWillBePrepared();

		// add items to simulation plan
		addFastExecutions(allExecutions, workerTimeToStart);
		addToDateExecutions(allExecutions, workerTimeToStart);
		addCheapExecutions(allExecutions);

		// handle unused workers
		stopUnusedWorkers();
		int workerPlanCount = simulationPlan.getWorkerPlanCount();
		workerManager.setDesiredWorkerCount(
				(int) Math.ceil(workerPlanCount * settings.getDesiredCreatedWorkerCountRatio()),
				(int) Math.ceil(workerPlanCount * settings.getDesiredRunningWorkerCountRatio()));
	}

	private void addFastExecutions(List<SimulationExecutionEntry> allExecutions, DateTime newWorkerStartedTime) {
		List<SimulationExecutionEntry> fastExecutions = filterExecutions(allExecutions,
				ExecutionEndSpecificationType.AsFastAsPossible);

		if (!fastExecutions.isEmpty()) {
			// add all entries to plan
			// always take worker which is first ready with current plan and add him new item,
			// if a new worker would be ready before any of current workers, start new worker and add him new item
			for (SimulationExecutionEntry execution : fastExecutions) {
				long estimateRunExecutionTimeInMillis = simulationTimeEstimator
						.estimateRunExecutionTimeInMillis(execution);

				for (SimulationRunEntry notStartedRun : execution.getNotStartedRuns()) {
					WorkerPlan firstEndingWorkerPlan = simulationPlan.getPlanForFirstEndingWorker();
					if (newWorkerStartedTime.isBefore(firstEndingWorkerPlan.getPlanEndTime())) {
						WorkerPlan startedWorkerPlan = startWorker();
						// if new worker can't be started, use worker with first ending plan
						firstEndingWorkerPlan = startedWorkerPlan != null ? startedWorkerPlan : firstEndingWorkerPlan;
					}
					addPlanItem(notStartedRun, estimateRunExecutionTimeInMillis, firstEndingWorkerPlan);
				}
			}
		}
	}

	private void addToDateExecutions(List<SimulationExecutionEntry> allExecutions, DateTime newWorkerStartedTime) {
		// sort executions to schedule items with nearest deadline first
		Comparator<SimulationExecutionEntry> comparator = (exec1, exec2) -> exec1.getDeadlineSettings().getEndDate()
				.compareTo(exec2.getDeadlineSettings().getEndDate());
		List<SimulationExecutionEntry> toDateExecutions = filterExecutions(allExecutions,
				ExecutionEndSpecificationType.ToDate).stream().sorted(comparator).collect(Collectors.toList());

		for (SimulationExecutionEntry execution : toDateExecutions) {
			long estimateRunExecutionTimeInMillis = simulationTimeEstimator.estimateRunExecutionTimeInMillis(execution);
			// get simulation run start deadline
			DateTime runStartDeadline = execution.getDeadlineSettings().getEndDate()
					.minus(estimateRunExecutionTimeInMillis);

			for (SimulationRunEntry notStartedRun : execution.getNotStartedRuns()) {
				// try to schedule runs to use the least possible number of workers with meeting all deadlines
				WorkerPlan workerPlan = simulationPlan.getPlanForLastEndingWorkerBefore(runStartDeadline);
				if (workerPlan == null) {
					// check if it is possible to start new worker to meet the deadline
					if (newWorkerStartedTime.isBefore(runStartDeadline)) {
						WorkerPlan startedWorkerPlan = startWorker();
						workerPlan = startedWorkerPlan != null ? startedWorkerPlan : workerPlan;
					}
					// no other worker could not be started -> use first possible worker
					if (workerPlan == null) {
						workerPlan = simulationPlan.getPlanForFirstEndingWorker();
					}
				}

				addPlanItem(notStartedRun, estimateRunExecutionTimeInMillis, workerPlan);
			}
		}
	}

	private void addCheapExecutions(List<SimulationExecutionEntry> allExecutions) {
		// run cheapest only if there is no running and no planned simulation run
		// run maximum one run at one time, don't plan anything after first run
		if (!simulationPlan.isAnythingPlanned()) {
			List<SimulationExecutionEntry> cheapExecutions = filterExecutions(allExecutions,
					ExecutionEndSpecificationType.AsCheapAsPossible);

			for (SimulationExecutionEntry execution : cheapExecutions) {
				for (SimulationRunEntry notStartedRun : execution.getNotStartedRuns()) {
					long estimateRunExecutionTimeInMillis = simulationTimeEstimator
							.estimateRunExecutionTimeInMillis(execution);
					WorkerPlan firstEndingWorkerPlan = simulationPlan.getPlanForFirstEndingWorker();
					addPlanItem(notStartedRun, estimateRunExecutionTimeInMillis, firstEndingWorkerPlan);
					return;
				}
			}
		}
	}

	private List<SimulationExecutionEntry> filterExecutions(List<SimulationExecutionEntry> executions,
			ExecutionEndSpecificationType type) {

		return executions.stream().filter(x -> x.getDeadlineSettings().getEndSpecificationType() == type)
				.collect(Collectors.toList());
	}

	private WorkerPlan startWorker() {
		if (settings.getMaximumNumberOfWorkers() < workerManager.getCurrentAvailableWorkerCount()) {
			WorkerInstance newWorker = workerManager.startWorker();
			return simulationPlan.addWorkerPlan(newWorker);
		}
		return null;
	}

	private void addPlanItem(SimulationRunEntry notStartedRun, long estimateRunExecutionTimeInMillis,
			WorkerPlan workerPlan) {
		DateTime itemStartTime = workerPlan.getPlanEndTime();
		DateTime itemEndTime = itemStartTime.plus(estimateRunExecutionTimeInMillis);
		WorkerPlanItemImpl newPlanItem = new WorkerPlanItemImpl(notStartedRun, itemStartTime, itemEndTime);
		workerPlan.addNextItem(newPlanItem);
	}

	private void stopUnusedWorkers() {

		List<WorkerPlan> removedWorkerPlans = simulationPlan.clearEmptyWorkerPlans();
		
		for (WorkerPlan removedWorkerPlan : removedWorkerPlans) {
			if (workerManager.getCurrentAvailableWorkerCount() <= 1) {
				// we want at least one worker to run
				return;
			}
			workerManager.stopWorker(removedWorkerPlan.getWorker());
		}
	}
}
