package cz.cuni.mff.d3s.jdeeco.cloudsimulator.worker.connectors;

import java.io.Serializable;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;

import org.springframework.jms.core.JmsTemplate;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.SimulationStatus;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.tasks.WorkerTask;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.updates.JobManagerUpdate;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.updates.SimulationStatusUpdate;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.updates.SimulationStatusUpdateImpl;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.updates.WorkerStatus;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.updates.WorkerStatusUpdate;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.updates.WorkerStatusUpdateImpl;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.worker.WorkerInfoProvider;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.worker.engine.WorkerTaskQueue;

public class JobManagerConnectorImpl implements JobManagerConnector {

	private final String workerId;

	private boolean isConnected = false;
	private Thread thread;

	private String incomingQueue;
	private String outgoingQueue;

	private WorkerTaskQueue workerTaskQueue;
	private JmsTemplate jmsTemplate;

	public JobManagerConnectorImpl(WorkerTaskQueue workerTaskQueue, WorkerInfoProvider workerInfoProvider,
			JmsTemplate jmsTemplate) {
		this.workerId = workerInfoProvider.getWorkerId();
		this.workerTaskQueue = workerTaskQueue;
		this.jmsTemplate = jmsTemplate;
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

				if (data instanceof WorkerTask) {
					workerTaskQueue.add((WorkerTask) data);
				} else {
					throw new RuntimeException("Incorrect message data (receiving only WorkerTask): " + data.toString());
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
		jmsTemplate.convertAndSend(outgoingQueue, update);

	}

	public void setIncomingQueue(String incomingQueue) {
		this.incomingQueue = incomingQueue;
	}

	public void setOutgoingQueue(String outgoingQueue) {
		this.outgoingQueue = outgoingQueue;
	}
}
