package cz.cuni.mff.d3s.jdeeco.cloudsimulator.worker.engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.PathEx;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.SimulationStatus;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.WorkerStatus;
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

	private final JobManagerConnector jobManagerConnector;
	private final SimulationExecutorFactory simulationExecutorFactory;

	private final SimulationDataManager simulationDataManager;
	private final HashMap<Integer, RunSimulationTask> incompleteTasks = new HashMap<Integer, RunSimulationTask>();
	private final List<SimulationExecutor> runningExecutors = new ArrayList<SimulationExecutor>();

	public SimulationManagerImpl(String executionParentDirectory, JobManagerConnector jobManagerConnector,
			SimulationDataManagerFactory simulationDataManagerFactory,
			SimulationExecutorFactory simulationExecutorFactory) {

		this.jobManagerConnector = jobManagerConnector;
		this.simulationExecutorFactory = simulationExecutorFactory;

		this.simulationDataManager = simulationDataManagerFactory.create(
				PathEx.combine(executionParentDirectory, "data"), PathEx.combine(executionParentDirectory, "logs"),
				this);
	}

	@Override
	public void runSimulation(RunSimulationTask task) {

		// prepare data
		synchronized (incompleteTasks) {
			incompleteTasks.put(task.getSimulationRunId(), task);
		}
		simulationDataManager.prepareData(task.getSimulationRunId(), task.getDataName());
	}

	@Override
	public void dataPrepared(int simulationRunId, SimulationData data) {
		SimulationExecutorParameters parameters = new SimulationExecutorParametersImpl(simulationRunId, data);
		SimulationExecutor executor = simulationExecutorFactory.create(this, parameters);

		synchronized (runningExecutors) {
			runningExecutors.add(executor);
		}
		executor.start();
	}

	@Override
	public void stopSimulation(StopSimulationTask task) {
		synchronized (runningExecutors) {
			List<SimulationExecutor> executorsToStop = runningExecutors.stream()
					.filter(x -> x.getParameters().getSimulationRunId() == task.getSimulationRunId())
					.collect(Collectors.toList());
			for (SimulationExecutor simulationExecutor : executorsToStop) {
				simulationExecutor.stop();
			}
		}
	}

	@Override
	public void executionEnded(SimulationExecutor simulationExecutor) {
		SimulationExecutorParameters parameters = simulationExecutor.getParameters();
		RunSimulationTask task = incompleteTasks.get(parameters.getSimulationRunId());

		simulationDataManager.saveResults(parameters.getSimulationRunId(), parameters.getSimulationData(),
				task.getDataName());
		removeExecutor(simulationExecutor);
	}

	@Override
	public void resultsSaved(int simulationRunId) {
		jobManagerConnector.sendSimulationStatusUpdate(simulationRunId, SimulationStatus.Completed);
		jobManagerConnector.sendWorkerStatusUpdate(WorkerStatus.Started);
	}

	@Override
	public void executionStopped(SimulationExecutor simulationExecutor) {
		jobManagerConnector.sendSimulationStatusUpdate(simulationExecutor.getParameters().getSimulationRunId(),
				SimulationStatus.Stopped);
		jobManagerConnector.sendWorkerStatusUpdate(WorkerStatus.Started);

		removeExecutor(simulationExecutor);
	}

	@Override
	public void exceptionOccured(SimulationExecutor simulationExecutor, Exception e) {
		jobManagerConnector.sendSimulationStatusUpdate(simulationExecutor.getParameters().getSimulationRunId(), e);
		jobManagerConnector.sendWorkerStatusUpdate(WorkerStatus.Started);

		removeExecutor(simulationExecutor);
	}

	private void removeExecutor(SimulationExecutor simulationExecutor) {

		synchronized (incompleteTasks) {
			incompleteTasks.remove(simulationExecutor.getParameters().getSimulationRunId());
		}

		synchronized (runningExecutors) {
			runningExecutors.remove(simulationExecutor);
		}
	}
}
