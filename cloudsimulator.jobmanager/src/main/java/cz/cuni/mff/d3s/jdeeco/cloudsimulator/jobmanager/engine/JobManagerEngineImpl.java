package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.connectors.WorkerConnector;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.control.UpdateExecutionsCommand;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.data.SimulationExecutionEntry;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.data.SimulationManager;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.data.SimulationRunEntry;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.pack.PackagePreparedUpdate;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.planning.SimulationPlan;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.planning.SimulationScheduler;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.planning.WorkerPlan;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.planning.WorkerPlanItem;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.workers.WorkerManager;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.tasks.RunSimulationTask;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.tasks.RunSimulationTaskImpl;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.updates.JobManagerUpdate;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.updates.SimulationStatusUpdate;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.updates.WorkerStatusUpdate;

public class JobManagerEngineImpl implements JobManagerEngine {

	private WorkerManager workerManager;
	private SimulationManager simulationManager;
	private SimulationScheduler simulationScheduler;
	private WorkerConnector workerConnector;
	private JobManagerUpdateQueue jobManagerUpdateQueue;

	private long queueTimeout = 3;
	private TimeUnit queueTimeoutUnit = TimeUnit.MINUTES;
	private boolean stopped = false;

	public JobManagerEngineImpl(WorkerManager workerManager, SimulationManager simulationManager,
			SimulationScheduler simulationScheduler, WorkerConnector workerConnector,
			JobManagerUpdateQueue jobManagerUpdateQueue) {
		this.workerManager = workerManager;
		this.simulationManager = simulationManager;
		this.simulationScheduler = simulationScheduler;
		this.workerConnector = workerConnector;
		this.jobManagerUpdateQueue = jobManagerUpdateQueue;
	}

	@Override
	public void start() {

		simulationManager.refreshExecutions();
		workerConnector.connect();

		while (!stopped) {
			List<JobManagerUpdate> updates = getNewUpdates();
			applyUpdates(updates);
			simulationScheduler.recalculateSchedule();
			updateWorkers();
		}

		workerConnector.disconnect();
	}

	@Override
	public void stop() {
		stopped = true;
	}

	private List<JobManagerUpdate> getNewUpdates() {
		List<JobManagerUpdate> updates;
		try {
			updates = jobManagerUpdateQueue.takeAll(queueTimeout, queueTimeoutUnit);
		} catch (InterruptedException e) {
			e.printStackTrace();
			updates = new ArrayList<JobManagerUpdate>();
		}
		return updates;
	}

	private void applyUpdates(List<JobManagerUpdate> updates) {
		if (updates.isEmpty()) {
			return;
		}

		// updates
		// update worker manager
		Stream<WorkerStatusUpdate> workerStatusUpdates = takeUpdates(updates, WorkerStatusUpdate.class);
		workerManager.update(workerStatusUpdates.collect(Collectors.toList()));

		// update simulation manager
		Stream<SimulationStatusUpdate> simulationStatusUpdates = takeUpdates(updates, SimulationStatusUpdate.class);
		simulationManager.updateStatus(simulationStatusUpdates.collect(Collectors.toList()));
		Stream<PackagePreparedUpdate> packagePreparedUpdates = takeUpdates(updates, PackagePreparedUpdate.class);
		simulationManager.updatePackageNames(packagePreparedUpdates.collect(Collectors.toList()));

		// commands
		// are there any other updates?
		Stream<UpdateExecutionsCommand> updateExecutionsCommands = takeUpdates(updates, UpdateExecutionsCommand.class);
		if (updateExecutionsCommands.findAny().isPresent()) {
			simulationManager.refreshExecutions();
		}

		if (!updates.isEmpty()) {
			throw new UnknownJobManagerUpdateException("Unknown job manager updates: " + updates);
		}
	}

	private <T extends JobManagerUpdate> Stream<T> takeUpdates(List<JobManagerUpdate> updates, Class<T> type) {
		@SuppressWarnings("unchecked")
		Stream<T> taken = updates.stream().filter(x -> x.getClass().equals(type)).map(x -> (T) x);
		taken.forEach(x -> updates.remove(x));
		return taken;
	}

	private void updateWorkers() {
		SimulationPlan simulationPlan = simulationScheduler.getSimulationPlan();
		List<WorkerPlan> workerPlans = simulationPlan.getWorkerPlans();
		for (WorkerPlan workerPlan : workerPlans) {
			WorkerPlanItem itemToRun = getPlanItemToRun(workerPlan);
			if (itemToRun != null) {
				SimulationRunEntry run = itemToRun.getSimulationRun();
				SimulationExecutionEntry execution = run.getExecution();

				// set run as started
				execution.startSimulationRun(run);

				// send run simulation task
				String workerId = workerPlan.getWorker().getWorkerId();
				RunSimulationTask task = new RunSimulationTaskImpl(run.getId(), execution.getPackageName());
				workerConnector.sendTask(workerId, task);
			}
		}
	}

	private WorkerPlanItem getPlanItemToRun(WorkerPlan workerPlan) {
		WorkerPlanItem currentItem = workerPlan.getCurrentItem();
		if (hasPreparedPackage(currentItem)) {
			return currentItem;
		}

		// TODO is this OK?
		for (WorkerPlanItem planItem : workerPlan) {
			if (hasPreparedPackage(planItem)) {
				return planItem;
			}
		}

		return null;
	}

	private boolean hasPreparedPackage(WorkerPlanItem workerPlanItem) {
		return workerPlanItem.getSimulationRun().getExecution().getPackageName() != null;
	}
}
