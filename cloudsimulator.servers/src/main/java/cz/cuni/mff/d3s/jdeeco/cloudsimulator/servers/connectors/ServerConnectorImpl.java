package cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.connectors;

import java.io.Serializable;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.data.TimeSpan;

public abstract class ServerConnectorImpl implements ServerConnector {

	private static final Logger logger = LoggerFactory.getLogger(ServerConnectorImpl.class);

	private final JmsTemplate jmsTemplate;
	private final String incomingQueue;

	private boolean isConnected = false;
	private Thread thread;

	protected ServerConnectorImpl(JmsTemplate jmsTemplate, TimeSpan receiveTimeout, String incomingQueue) {
		this.jmsTemplate = jmsTemplate;
		this.incomingQueue = incomingQueue;
		
		jmsTemplate.setReceiveTimeout(receiveTimeout.getTotalMilliseconds());
	}

	@Override
	public void connect() {
		if (isConnected) {
			return;
		}
		this.isConnected = true;
		
		logger.info("Connecting to queue '{}'.", incomingQueue);

		Runnable listen = () -> this.listenToMessages();

		this.thread = new Thread(listen);
		this.thread.start();
	}

	private void listenToMessages() {
		try {
			while (true) {
				Message message = jmsTemplate.receive(incomingQueue);
				if (message != null) {
					processIncomingMessage(message);
				}
			}
		} catch (Exception e) {
			logger.error(String.format("Error occurred while listening to messages from queue '%s'.", incomingQueue), e);
		}
	}

	private void processIncomingMessage(Message message) {
		logger.debug("Processing message '{}'.", message);
		
		if (message instanceof ObjectMessage) {
			try {
				Serializable data = ((ObjectMessage) message).getObject();

				processIncomingMessageData(data);
			} catch (JMSException e) {
				throw new RuntimeException("Error occured while getting data from message.", e);
			}
		} else {
			throw new RuntimeException("Incorrect message type: " + message.toString());
		}
	}

	protected abstract void processIncomingMessageData(Serializable data);

	protected void sendMessage(String outgoingQueue, Serializable data) {
		logger.debug("Sending message with data '{}' to queue '{}'.", outgoingQueue, data);
		
		jmsTemplate.convertAndSend(outgoingQueue, data);
	}

	@Override
	public void disconnect() {
		this.isConnected = false;

		logger.info("Disconnecting from queue '{}'.", incomingQueue);
		
		try {
			this.thread.join();
		} catch (InterruptedException e) {
			logger.error(String.format("Error occurred while disconnecting from queue '%s'.", incomingQueue), e);
		}
	}
}
