package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.connectors;

import java.io.Serializable;

import org.apache.log4j.Logger;
import org.springframework.jms.core.JmsTemplate;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.data.TimeSpan;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.JobManagerUpdateQueue;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.connectors.ServerConnectorImpl;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.tasks.WorkerTask;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.updates.JobManagerUpdate;

public class WorkerConnectorImpl extends ServerConnectorImpl implements WorkerConnector {

	private final Logger logger = Logger.getLogger(WorkerConnectorImpl.class);
	
	private final JobManagerUpdateQueue jobManagerUpdateQueue;
	private final String outgoingQueuePrefix;

	protected WorkerConnectorImpl(JmsTemplate jmsTemplate, TimeSpan receiveTimeout, String incomingQueue,
			String outgoingQueuePrefix, JobManagerUpdateQueue jobManagerUpdateQueue) {
		super(jmsTemplate, receiveTimeout, incomingQueue);

		this.outgoingQueuePrefix = outgoingQueuePrefix;
		this.jobManagerUpdateQueue = jobManagerUpdateQueue;
	}

	@Override
	protected void processIncomingMessageData(Serializable data) {
		logger.info("Receiving message from worker. Data: " + data);
		
		if (data instanceof JobManagerUpdate) {
			jobManagerUpdateQueue.add((JobManagerUpdate) data);
		} else {
			logger.error("Incorrect message data (receiving only JobManagerUpdate): " + data);
		}
	}

	@Override
	public void sendTask(String workerId, WorkerTask task) {
		logger.info(String.format("Sending taks to worker %s. Task: %s.", workerId, task));
		
		sendMessage(outgoingQueuePrefix + workerId, task);
	}
}
