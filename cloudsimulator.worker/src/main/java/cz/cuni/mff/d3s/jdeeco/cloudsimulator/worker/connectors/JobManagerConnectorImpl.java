package cz.cuni.mff.d3s.jdeeco.cloudsimulator.worker.connectors;

import java.io.Serializable;

import org.springframework.jms.core.JmsTemplate;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.SimulationStatus;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.WorkerStatus;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.connectors.ServerConnectorImpl;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.tasks.WorkerTask;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.updates.JobManagerUpdate;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.updates.SimulationStatusUpdate;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.updates.SimulationStatusUpdateImpl;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.updates.WorkerStatusUpdate;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.updates.WorkerStatusUpdateImpl;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.worker.WorkerInfoProvider;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.worker.engine.WorkerTaskQueue;

public class JobManagerConnectorImpl extends ServerConnectorImpl implements JobManagerConnector {

	private final String workerId;

	private WorkerTaskQueue workerTaskQueue;

	public JobManagerConnectorImpl(JmsTemplate jmsTemplate, String incomingQueue, String outgoingQueuePrefix,
			WorkerTaskQueue workerTaskQueue, WorkerInfoProvider workerInfoProvider) {
		super(jmsTemplate, incomingQueue, outgoingQueuePrefix);

		this.workerId = workerInfoProvider.getWorkerId();
		this.workerTaskQueue = workerTaskQueue;
	}

	@Override
	protected void processIncomingMessageData(Serializable data) {
		if (data instanceof WorkerTask) {
			workerTaskQueue.add((WorkerTask) data);
		} else {
			throw new RuntimeException("Incorrect message data (receiving only WorkerTask): " + data.toString());
		}
	}

	@Override
	public void sendSimulationStatusUpdate(int simulationRunId, SimulationStatus status) {
		SimulationStatusUpdate update = new SimulationStatusUpdateImpl(workerId, simulationRunId, status);
		sendUpdate(update);
	}

	@Override
	public void sendSimulationStatusUpdate(int simulationRunId, Exception e) {
		SimulationStatusUpdate update = new SimulationStatusUpdateImpl(workerId, simulationRunId, e.getMessage());
		sendUpdate(update);
	}

	@Override
	public void sendWorkerStatusUpdate(WorkerStatus status) {
		WorkerStatusUpdate update = new WorkerStatusUpdateImpl(workerId, status);
		sendUpdate(update);
	}

	private void sendUpdate(JobManagerUpdate update) {
		sendMessage(workerId, update);
	}
}