package cz.cuni.mff.d3s.jdeeco.cloudsimulator.worker.engine;

import java.util.HashMap;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.data.SimulationExitReason;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.data.SimulationStatus;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.data.WorkerStatus;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.SimulationId;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.tasks.RunSimulationTask;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.tasks.StopSimulationTask;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.worker.connectors.JobManagerConnector;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.worker.data.SimulationData;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.worker.data.SimulationDataListener;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.worker.data.SimulationDataManager;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.worker.data.SimulationDataManagerFactory;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.worker.execution.ExecutionListener;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.worker.execution.SimulationExecutor;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.worker.execution.SimulationExecutorFactory;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.worker.execution.SimulationExecutorParameters;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.worker.execution.SimulationExecutorParametersImpl;

public class SimulationManagerImpl implements SimulationManager, ExecutionListener, SimulationDataListener {

	private final HashMap<SimulationId, TaskEntry> incompleteTasks = new HashMap<SimulationId, TaskEntry>();
	private final HashMap<SimulationId, SimulationStatusUpdater> finishedNotUpdatedSimulations = new HashMap<SimulationId, SimulationStatusUpdater>();

	private final JobManagerConnector jobManagerConnector;
	private final SimulationDataManager simulationDataManager;
	private final SimulationExecutorFactory simulationExecutorFactory;

	public SimulationManagerImpl(JobManagerConnector jobManagerConnector,
			SimulationDataManagerFactory simulationDataManagerFactory,
			SimulationExecutorFactory simulationExecutorFactory) {

		this.jobManagerConnector = jobManagerConnector;
		this.simulationExecutorFactory = simulationExecutorFactory;

		this.simulationDataManager = simulationDataManagerFactory.create(this);
	}

	@Override
	public void runSimulation(RunSimulationTask task) {

		// add to registry
		synchronized (incompleteTasks) {
			incompleteTasks.put(task.getSimulationId(), new TaskEntry(task));
		}

		// prepare data
		simulationDataManager.prepareData(task.getSimulationId(), task.getStartupFile());
	}

	@Override
	public void dataPrepared(SimulationId simulationId, SimulationData data) {

		SimulationExecutorParameters parameters = new SimulationExecutorParametersImpl(simulationId, data);
		SimulationExecutor executor = simulationExecutorFactory.create(this, parameters);
		boolean shouldStart = false;

		// assign to task in registry
		synchronized (incompleteTasks) {
			if (incompleteTasks.containsKey(simulationId)) { // task was not stopped
				incompleteTasks.get(simulationId).executor = executor;
				shouldStart = true;
			}
		}

		// start
		if (shouldStart) {
			executor.start();
		}
	}

	@Override
	public void stopSimulation(StopSimulationTask task) {
		SimulationExecutor simulationExecutor = null;

		// remove from registry
		synchronized (incompleteTasks) {
			if (incompleteTasks.containsKey(task.getSimulationId())) { // task was not stopped
				TaskEntry entry = incompleteTasks.remove(task.getSimulationId());
				simulationExecutor = entry.executor;
			}
		}

		// stop executor
		if (simulationExecutor != null) {
			simulationExecutor.stop();
		}
	}

	@Override
	public void executionEnded(SimulationExecutor simulationExecutor) {
		SimulationExecutorParameters parameters = simulationExecutor.getParameters();
		SimulationStatusUpdater updater = new ReasonedSimulationStatusUpdater(parameters.getSimulationId(),
				SimulationStatus.Completed, SimulationExitReason.Finished);

		saveAndUpdateManager(simulationExecutor, parameters, updater);
	}

	@Override
	public void executionManuallyStopped(SimulationExecutor simulationExecutor) {
		executionStopped(simulationExecutor, SimulationExitReason.RunExitCalled);
	}

	@Override
	public void executionStopped(SimulationExecutor simulationExecutor, SimulationExitReason exitReason) {
		SimulationExecutorParameters parameters = simulationExecutor.getParameters();
		SimulationStatusUpdater updater = new ReasonedSimulationStatusUpdater(parameters.getSimulationId(),
				SimulationStatus.Stopped, exitReason);

		saveAndUpdateManager(simulationExecutor, parameters, updater);
	}

	@Override
	public void exceptionOccured(SimulationExecutor simulationExecutor, Exception e) {
		SimulationExecutorParameters parameters = simulationExecutor.getParameters();
		SimulationStatusUpdater updater = new ExceptionSimulationStatusUpdater(parameters.getSimulationId(), e);

		saveAndUpdateManager(simulationExecutor, parameters, updater);
	}

	private void saveAndUpdateManager(SimulationExecutor simulationExecutor, SimulationExecutorParameters parameters,
			SimulationStatusUpdater updater) {
		// add to registry
		synchronized (finishedNotUpdatedSimulations) {
			finishedNotUpdatedSimulations.put(parameters.getSimulationId(), updater);
		}

		// save
		simulationDataManager.saveResults(parameters.getSimulationId(), parameters.getSimulationData());

		removeExecutor(simulationExecutor);
	}

	@Override
	public void resultsSaved(SimulationId simulationId) {
		SimulationStatusUpdater simulationStatusUpdater;
		synchronized (finishedNotUpdatedSimulations) {
			simulationStatusUpdater = finishedNotUpdatedSimulations.remove(simulationId);
		}

		simulationStatusUpdater.Update();
		jobManagerConnector.sendWorkerStatusUpdate(WorkerStatus.Started);
	}

	private void removeExecutor(SimulationExecutor simulationExecutor) {

		synchronized (incompleteTasks) {
			incompleteTasks.remove(simulationExecutor.getParameters().getSimulationId());
		}
	}

	private class TaskEntry {
		RunSimulationTask task;
		SimulationExecutor executor;

		public TaskEntry(RunSimulationTask task) {
			this.task = task;
		}

		@Override
		public String toString() {
			return String.format("TaskEntry [task=%s, executor=%s]", task, executor);
		}
	}

	private interface SimulationStatusUpdater {
		void Update();
	}

	private abstract class SimulationStatusUpdaterBase implements SimulationStatusUpdater {
		protected final SimulationId simulationId;

		public SimulationStatusUpdaterBase(SimulationId simulationId) {
			this.simulationId = simulationId;
		}
	}

	private class ReasonedSimulationStatusUpdater extends SimulationStatusUpdaterBase {

		private final SimulationStatus simulationStatus;
		private final SimulationExitReason exitReason;

		public ReasonedSimulationStatusUpdater(SimulationId simulationId, SimulationStatus simulationStatus,
				SimulationExitReason exitReason) {
			super(simulationId);
			// TODO Auto-generated constructor stub
			this.simulationStatus = simulationStatus;
			this.exitReason = exitReason;
		}

		@Override
		public void Update() {
			jobManagerConnector.sendSimulationStatusUpdate(simulationId, simulationStatus, exitReason);
		}
	}

	private class ExceptionSimulationStatusUpdater extends SimulationStatusUpdaterBase {

		private final Exception exception;

		public ExceptionSimulationStatusUpdater(SimulationId simulationId, Exception exception) {
			super(simulationId);
			this.exception = exception;
		}

		@Override
		public void Update() {
			jobManagerConnector.sendSimulationStatusUpdate(simulationId, exception);
		}
	}

}
