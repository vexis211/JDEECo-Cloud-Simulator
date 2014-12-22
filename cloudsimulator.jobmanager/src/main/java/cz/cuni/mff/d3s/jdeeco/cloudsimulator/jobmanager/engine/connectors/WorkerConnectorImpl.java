package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.connectors;

import java.io.Serializable;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;

import org.springframework.jms.core.JmsTemplate;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.JobManagerUpdateQueue;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.tasks.WorkerTask;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.updates.JobManagerUpdate;

public class WorkerConnectorImpl implements WorkerConnector {

	private boolean isConnected = false;
	private Thread thread;

	private String incomingQueue;
	private String outgoingQueuePrefix;

	@Resource
	private JobManagerUpdateQueue jobManagerUpdateQueue;

	@Resource
	private JmsTemplate jmsTemplate;

	@Override
	public void connect() {
		if (isConnected) return;
		this.isConnected = true;

		Runnable listen = () -> this.listenToMessages();

		this.thread = new Thread(listen);
		this.thread.start();
	}

	private void listenToMessages() {
		try {
			while (true) {
				Message message = jmsTemplate.receive(incomingQueue);

				processIncomingMessage(message);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void processIncomingMessage(Message message) {
		if (message instanceof ObjectMessage) {
			try {
				Serializable data = ((ObjectMessage) message).getObject();

				if (data instanceof JobManagerUpdate) {
					jobManagerUpdateQueue.add((JobManagerUpdate) data);
				} else {
					throw new RuntimeException("Incorrect message data (receiving only JobManagerUpdate): "
							+ data.toString());
				}
			} catch (JMSException e) {
				throw new RuntimeException("Error occured while getting data from message.", e);
			}
		} else {
			throw new RuntimeException("Incorrect message type: " + message.toString());
		}
	}

	@Override
	public void disconnect() {
		this.thread.interrupt(); // TODO do better
		this.isConnected = false;
	}

	@Override
	public void sendTask(String workerId, WorkerTask task) {
		jmsTemplate.convertAndSend(outgoingQueuePrefix + workerId, task);
	}

	public void setIncomingQueue(String incomingQueue) {
		this.incomingQueue = incomingQueue;
	}

	public void setOutgoingQueuePrefix(String outgoingQueuePrefix) {
		this.outgoingQueuePrefix = outgoingQueuePrefix;
	}
}
