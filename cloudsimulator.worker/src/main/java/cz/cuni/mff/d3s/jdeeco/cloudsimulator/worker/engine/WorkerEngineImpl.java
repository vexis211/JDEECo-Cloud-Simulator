package cz.cuni.mff.d3s.jdeeco.cloudsimulator.worker.engine;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.data.TimeSpan;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.data.WorkerStatus;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.tasks.RunSimulationTask;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.tasks.StopSimulationTask;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.tasks.WorkerTask;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.worker.connectors.JobManagerConnector;

public class WorkerEngineImpl implements WorkerEngine {

	private static final Logger logger = LoggerFactory.getLogger(WorkerEngineImpl.class);

	private final SimulationManager simulationManager;

	private final JobManagerConnector jobManagerConnector;
	private final WorkerTaskQueue workerTaskQueue;
	private final TimeSpan receiveMessageQueueTimeout;

	private boolean stopped = false;

	public WorkerEngineImpl(SimulationManager simulationManager, JobManagerConnector jobManagerConnector,
			WorkerTaskQueue workerTaskQueue, TimeSpan receiveMessageQueueTimeout) {
		this.simulationManager = simulationManager;
		this.jobManagerConnector = jobManagerConnector;
		this.workerTaskQueue = workerTaskQueue;
		this.receiveMessageQueueTimeout = receiveMessageQueueTimeout;
	}

	@Override
	public void start() {

		logger.info("Starting worker...");

		jobManagerConnector.connect();
		jobManagerConnector.sendWorkerStatusUpdate(WorkerStatus.Started);

		logger.info("Starting to listen to messages.");
		
		while (!stopped) {
			List<WorkerTask> tasks = getNewTasks();
			processTasks(tasks);
		}

		logger.info("Stopping to listen to messages and ending.");
	}

	@Override
	public void stop() {
		logger.info("Stopping worker... All messages which were received till this time will be processed and then worker stops.");
		stopped = true;
	}

	private List<WorkerTask> getNewTasks() {
		List<WorkerTask> tasks;
		try {
			tasks = workerTaskQueue.takeAll(receiveMessageQueueTimeout.getNumberOUnits(),
					receiveMessageQueueTimeout.getUnit());
		} catch (InterruptedException e) {
			logger.error("Error occured while getting new tasks.", e);
			tasks = new ArrayList<WorkerTask>();
		}
		return tasks;
	}

	private void processTasks(List<WorkerTask> tasks) {
		if (tasks.isEmpty()) {
			return;
		}

		logger.info("Processing tasks...");
		tasks.forEach(x -> processTask(x));
	}

	private void processTask(WorkerTask task) {

		logger.info("Processing task '{}'", task);
		
		if (task instanceof RunSimulationTask) {
			simulationManager.runSimulation((RunSimulationTask) task);
		} else if (task instanceof StopSimulationTask) {
			simulationManager.stopSimulation((StopSimulationTask) task);
		} else {
			logger.error("Unknown worker task: {}", task);
		}
	}
}
