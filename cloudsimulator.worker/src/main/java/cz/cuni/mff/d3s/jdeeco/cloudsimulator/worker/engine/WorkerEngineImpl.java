package cz.cuni.mff.d3s.jdeeco.cloudsimulator.worker.engine;

import java.util.ArrayList;
import java.util.List;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.WorkerStatus;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.tasks.RunSimulationTask;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.tasks.StopSimulationsTask;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.tasks.WorkerTask;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.worker.connectors.JobManagerConnector;

public class WorkerEngineImpl implements WorkerEngine {

	private final WorkerTaskQueue workerTaskQueue;
	private final JobManagerConnector jobManagerConnector;
	private final SimulationManager simulationManager;

	public WorkerEngineImpl(WorkerTaskQueue workerTaskQueue, JobManagerConnector jobManagerConnector,
			SimulationManager simulationManager) {
		this.workerTaskQueue = workerTaskQueue;
		this.jobManagerConnector = jobManagerConnector;
		this.simulationManager = simulationManager;
	}

	@Override
	public void start() {

		jobManagerConnector.connect();
		jobManagerConnector.sendWorkerStatusUpdate(WorkerStatus.Started);

		while (true) {
			List<WorkerTask> tasks = getNewTasks();
			processTasks(tasks);
		}
	}

	private List<WorkerTask> getNewTasks() {
		List<WorkerTask> tasks;
		try {
			tasks = workerTaskQueue.takeAll();
		} catch (InterruptedException e) {
			e.printStackTrace();
			tasks = new ArrayList<WorkerTask>();
		}
		return tasks;
	}

	private void processTasks(List<WorkerTask> tasks) {
		tasks.forEach(x -> processTask(x));
	}

	private void processTask(WorkerTask task) {

		if (task instanceof RunSimulationTask) {
			simulationManager.runSimulation((RunSimulationTask) task);
		} else if (task instanceof StopSimulationsTask) {
			simulationManager.stopSimulations();
		} else {
			throw new UnknownWorkerTaskException("Unknown worker task: " + task);
		}
	}
}
