package cz.cuni.mff.d3s.jdeeco.cloudsimulator.worker.engine;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.tasks.ResetMachineTask;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.tasks.RunSimulationTask;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.tasks.WorkerTask;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.worker.connectors.JobManagerConnector;

public class WorkerEngineImpl implements WorkerEngine {

	@Resource
	private WorkerTaskQueue workerTaskQueue;

	@Resource
	private JobManagerConnector jobManagerConnector;

	@Resource
	private SimulationManager simulationManager;

	@Override
	public void start() {

		jobManagerConnector.connect();
		
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
			simulationManager.runSimulation();
		}
		else if (task instanceof ResetMachineTask) {
			simulationManager.stopAndCleanSimulations();
		}
		else {
			throw new UnknownWorkerTaskException("Unknown worker task: " + task);
		}
	}

}
