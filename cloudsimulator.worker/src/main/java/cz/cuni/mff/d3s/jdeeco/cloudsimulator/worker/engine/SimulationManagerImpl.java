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
	private final HashMap<SimulationId, SimulationExitReason> notPublishedExitReasons = new HashMap<SimulationId, SimulationExitReason>();

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
	public void executionEnded(SimulationExecutor simulationExecutor, SimulationExitReason exitReason) {
		SimulationExecutorParameters parameters = simulationExecutor.getParameters();

		synchronized (notPublishedExitReasons) {
			notPublishedExitReasons.put(parameters.getSimulationId(), exitReason);
		}
		
		simulationDataManager.saveResults(parameters.getSimulationId(), parameters.getSimulationData());
		removeExecutor(simulationExecutor);
	}

	@Override
	public void resultsSaved(SimulationId simulationId) {
		SimulationExitReason exitReason;
		synchronized (notPublishedExitReasons) {
			exitReason = notPublishedExitReasons.remove(simulationId);
		}
				
		jobManagerConnector.sendSimulationStatusUpdate(simulationId, SimulationStatus.Completed, exitReason);
		jobManagerConnector.sendWorkerStatusUpdate(WorkerStatus.Started);
	}

	@Override
	public void executionStopped(SimulationExecutor simulationExecutor, SimulationExitReason exitReason) {
		jobManagerConnector.sendSimulationStatusUpdate(simulationExecutor.getParameters().getSimulationId(),
				SimulationStatus.Stopped, exitReason);
		jobManagerConnector.sendWorkerStatusUpdate(WorkerStatus.Started);

		removeExecutor(simulationExecutor);
	}

	@Override
	public void exceptionOccured(SimulationExecutor simulationExecutor, Exception e) {
		jobManagerConnector.sendSimulationStatusUpdate(simulationExecutor.getParameters().getSimulationId(), e);
		jobManagerConnector.sendWorkerStatusUpdate(WorkerStatus.Started);

		removeExecutor(simulationExecutor);
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
}
