package cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.connectors;

import java.io.Serializable;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;

import org.springframework.jms.core.JmsTemplate;

public abstract class ServerConnectorImpl implements ServerConnector {

	private boolean isConnected = false;
	private Thread thread;

	private final String incomingQueue;

	protected final JmsTemplate jmsTemplate;

	protected ServerConnectorImpl(JmsTemplate jmsTemplate, String incomingQueue) {
		this.jmsTemplate = jmsTemplate;
		this.incomingQueue = incomingQueue;
	}

	@Override
	public void connect() {
		if (isConnected)
			return;
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
		jmsTemplate.convertAndSend(outgoingQueue, data);
	}

	@Override
	public void disconnect() {
		this.thread.interrupt(); // TODO do better
		this.isConnected = false;
	}
}
