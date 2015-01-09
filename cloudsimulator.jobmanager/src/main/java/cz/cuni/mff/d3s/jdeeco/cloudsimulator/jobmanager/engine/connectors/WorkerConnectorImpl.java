package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.connectors;

import java.io.Serializable;

import org.springframework.jms.core.JmsTemplate;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.JobManagerUpdateQueue;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.connectors.ServerConnectorImpl;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.tasks.WorkerTask;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.updates.JobManagerUpdate;

public class WorkerConnectorImpl extends ServerConnectorImpl implements WorkerConnector {

	private final JobManagerUpdateQueue jobManagerUpdateQueue;
	private final String outgoingQueuePrefix;

	protected WorkerConnectorImpl(JmsTemplate jmsTemplate, String incomingQueue, String outgoingQueuePrefix,
			JobManagerUpdateQueue jobManagerUpdateQueue) {
		super(jmsTemplate, incomingQueue);
		
		this.outgoingQueuePrefix = outgoingQueuePrefix;
		this.jobManagerUpdateQueue = jobManagerUpdateQueue;
	}

	@Override
	protected void processIncomingMessageData(Serializable data) {
		if (data instanceof JobManagerUpdate) {
			jobManagerUpdateQueue.add((JobManagerUpdate) data);
		} else {
			throw new RuntimeException("Incorrect message data (receiving only JobManagerUpdate): " + data.toString());
		}
	}

	@Override
	public void sendTask(String workerId, WorkerTask task) {
		sendMessage(outgoingQueuePrefix + workerId, task);
	}
}
