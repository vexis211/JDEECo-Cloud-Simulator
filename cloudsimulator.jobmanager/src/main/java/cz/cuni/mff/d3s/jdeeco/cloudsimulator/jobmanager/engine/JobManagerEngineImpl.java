package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.data.SimulationStatus;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.data.TimeSpan;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.data.WorkerStatus;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.connectors.WorkerConnector;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.SimulationExecutionEntry;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.SimulationManager;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.SimulationRunEntry;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.pack.PackagePreparedUpdate;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.planning.SimulationPlan;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.planning.SimulationScheduler;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.planning.WorkerPlan;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.planning.WorkerPlanItem;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.workers.WorkerInstance;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.workers.WorkerManager;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.SimulationId;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.SimulationIdImpl;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.commands.UpdateExecutionsCommand;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.tasks.RunSimulationTask;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.tasks.RunSimulationTaskImpl;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.tasks.StopSimulationTask;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.tasks.StopSimulationTaskImpl;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.updates.JobManagerUpdate;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.updates.SimulationStatusUpdate;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.updates.WorkerStatusUpdate;

public class JobManagerEngineImpl implements JobManagerEngine {

	private final Logger logger = Logger.getLogger(JobManagerEngineImpl.class);

	private final WorkerManager workerManager;
	private final SimulationManager simulationManager;
	private final SimulationScheduler simulationScheduler;

	private final WorkerConnector workerConnector;
	private final JobManagerUpdateQueue jobManagerUpdateQueue;
	private final TimeSpan receiveMessageQueueTimeout;

	private boolean stopped = false;

	public JobManagerEngineImpl(WorkerManager workerManager, SimulationManager simulationManager,
			SimulationScheduler simulationScheduler, WorkerConnector workerConnector,
			JobManagerUpdateQueue jobManagerUpdateQueue, TimeSpan receiveMessageQueueTimeout) {
		this.workerManager = workerManager;
		this.simulationManager = simulationManager;
		this.simulationScheduler = simulationScheduler;
		this.workerConnector = workerConnector;
		this.jobManagerUpdateQueue = jobManagerUpdateQueue;
		this.receiveMessageQueueTimeout = receiveMessageQueueTimeout;
	}

	@Override
	public void start() {

		logger.info("Initializing job manager engine...");

		simulationManager.refreshExecutions();
		workerConnector.connect();

		logger.info("Starting job manager update loop...");

		while (!stopped) {
			List<JobManagerUpdate> updates = getNewUpdates();
			applyUpdates(updates);
			simulationScheduler.recalculateSchedule();
			updateWorkers();
		}

		logger.info("Disconnecting from worker connector...");

		workerConnector.disconnect();
	}

	@Override
	public void stop() {
		stopped = true;
	}

	private List<JobManagerUpdate> getNewUpdates() {
		List<JobManagerUpdate> updates;
		try {
			updates = jobManagerUpdateQueue.takeAll(receiveMessageQueueTimeout.getNumberOUnits(),
					receiveMessageQueueTimeout.getUnit());
		} catch (InterruptedException e) {
			logger.info("Error occured while getting new updates.", e);
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
		List<WorkerStatusUpdate> workerStatusUpdates = takeUpdates(updates, WorkerStatusUpdate.class);
		workerManager.update(workerStatusUpdates);

		// update simulation manager
		List<SimulationStatusUpdate> simulationStatusUpdates = takeUpdates(updates, SimulationStatusUpdate.class);
		simulationManager.updateStatus(simulationStatusUpdates);
		List<PackagePreparedUpdate> packagePreparedUpdates = takeUpdates(updates, PackagePreparedUpdate.class);
		simulationManager.updatePackageNames(packagePreparedUpdates);

		// commands
		// are there any other updates?
		List<UpdateExecutionsCommand> updateExecutionsCommands = takeUpdates(updates, UpdateExecutionsCommand.class);
		if (!updateExecutionsCommands.isEmpty()) {
			simulationManager.refreshExecutions();
		}

		if (!updates.isEmpty()) {
			logger.error("Unknown job manager updates: " + Arrays.toString(updates.toArray()));
		}
	}

	private <T extends JobManagerUpdate> List<T> takeUpdates(List<JobManagerUpdate> updates, Class<T> type) {
		@SuppressWarnings("unchecked")
		List<T> taken = updates.stream().filter(x -> type.isAssignableFrom(x.getClass())).map(x -> (T) x)
				.collect(Collectors.toList());
		taken.forEach(x -> updates.remove(x));
		return taken;
	}

	private void updateWorkers() {
		SimulationPlan simulationPlan = simulationScheduler.getSimulationPlan();
		List<WorkerPlan> workerPlans = simulationPlan.getWorkerPlans();
		for (WorkerPlan workerPlan : workerPlans) {

			WorkerPlanItem planItemRunning = getPlanItemRunning(workerPlan);
			// running simulation at this time
			if (planItemRunning != null) {
				SimulationRunEntry simulationRun = planItemRunning.getSimulationRun();
				if (simulationRun.getStatus() == SimulationStatus.Stopped) {
					stopSimulationRun(workerPlan.getWorker(), simulationRun);
				}
			}
			// not running simulation at this time
			else {
				// checked if worker is prepared and if anything is planned
				WorkerPlanItem itemToRun = getPlanItemToRun(workerPlan);
				if (itemToRun != null) {
					startSimulationRun(workerPlan.getWorker(), itemToRun.getSimulationRun());
				}
			}
		}
	}

	private void stopSimulationRun(WorkerInstance worker, SimulationRunEntry simulationRun) {
		// send stop simulation task
		String workerId = worker.getWorkerId();
		SimulationId simulationId = new SimulationIdImpl(simulationRun.getExecution().getId(), simulationRun.getId());
		StopSimulationTask task = new StopSimulationTaskImpl(simulationId);
		workerConnector.sendTask(workerId, task);
	}

	private void startSimulationRun(WorkerInstance worker, SimulationRunEntry simulationRun) {
		SimulationExecutionEntry execution = simulationRun.getExecution();

		// set run as started
		execution.startSimulationRun(simulationRun);

		// send run simulation task
		String workerId = worker.getWorkerId();
		SimulationId simulationId = new SimulationIdImpl(simulationRun.getExecution().getId(), simulationRun.getId());
		RunSimulationTask task = new RunSimulationTaskImpl(simulationId);
		workerConnector.sendTask(workerId, task);
	}

	private WorkerPlanItem getPlanItemRunning(WorkerPlan workerPlan) {
		WorkerStatus workerStatus = workerPlan.getWorker().getStatus();
		if (workerStatus == WorkerStatus.StartingSimulation || workerStatus == WorkerStatus.RunningSimulation) {
			return workerPlan.getCurrentItem();
		}
		return null;
	}

	private WorkerPlanItem getPlanItemToRun(WorkerPlan workerPlan) {

		if (workerPlan.getWorker().getStatus() != WorkerStatus.Started) {
			// we need to wait until worker is started
			return null;
		}

		WorkerPlanItem currentItem = workerPlan.getCurrentItem();
		if (hasPreparedPackage(currentItem)) {
			return currentItem;
		}

		// TODO is OK to prefer another package with another priority??
		for (WorkerPlanItem planItem : workerPlan) {
			if (hasPreparedPackage(planItem)) {
				return planItem;
			}
		}

		return null;
	}

	private boolean hasPreparedPackage(WorkerPlanItem workerPlanItem) {
		return workerPlanItem.getSimulationRun().getExecution().isPackagePrepared();
	}
}
