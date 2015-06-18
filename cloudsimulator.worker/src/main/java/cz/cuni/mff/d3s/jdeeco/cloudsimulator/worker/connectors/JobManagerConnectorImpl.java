package cz.cuni.mff.d3s.jdeeco.cloudsimulator.worker.connectors;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.jms.core.JmsTemplate;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.data.SimulationExitReason;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.data.SimulationStatus;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.data.TimeSpan;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.data.WorkerStatus;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.SimulationId;
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

	private final Logger logger = LoggerFactory.getLogger(JobManagerConnectorImpl.class);

	private final WorkerTaskQueue workerTaskQueue;
	private final String outgoingQueue;
	private final String workerId;

	public JobManagerConnectorImpl(JmsTemplate jmsTemplate, String incomingQueuePrefix, String outgoingQueue,
			WorkerTaskQueue workerTaskQueue, TimeSpan receiveTimeout, WorkerInfoProvider workerInfoProvider) {
		super(jmsTemplate, receiveTimeout, incomingQueuePrefix + workerInfoProvider.getWorkerId());

		this.workerTaskQueue = workerTaskQueue;
		this.outgoingQueue = outgoingQueue;
		this.workerId = workerInfoProvider.getWorkerId();
	}

	@Override
	protected void processIncomingMessageData(Serializable data) {
		logger.info("Receiving message from job manager. Data: {}", data);

		if (data instanceof WorkerTask) {
			workerTaskQueue.add((WorkerTask) data);
		} else {
			logger.error("Incorrect message data (receiving only WorkerTask): {}", data);
		}
	}

	@Override
	public void sendSimulationStatusUpdate(SimulationId simulationId, SimulationStatus status,
			SimulationExitReason exitReason) {
		SimulationStatusUpdate update = new SimulationStatusUpdateImpl(workerId, simulationId, status, exitReason);
		sendUpdate(update);
	}

	@Override
	public void sendSimulationStatusUpdate(SimulationId simulationId, Exception e) {
		SimulationStatusUpdate update = new SimulationStatusUpdateImpl(workerId, simulationId, e.getMessage());
		sendUpdate(update);
	}

	@Override
	public void sendWorkerStatusUpdate(WorkerStatus status) {
		WorkerStatusUpdate update = new WorkerStatusUpdateImpl(workerId, status);
		sendUpdate(update);
	}

	private void sendUpdate(JobManagerUpdate update) {
		logger.info("Sending update to job manager {}. Update: {}.", workerId, update);

		sendMessage(outgoingQueue, update);
	}
}