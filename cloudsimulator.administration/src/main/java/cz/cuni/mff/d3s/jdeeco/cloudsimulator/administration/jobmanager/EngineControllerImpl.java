package cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.jobmanager;

import java.io.Serializable;

import org.springframework.jms.core.JmsTemplate;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.commands.UpdateExecutionsCommandImpl;

/*
import javax.annotation.Resource;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.JobManagerUpdateQueue;
*/
public class EngineControllerImpl implements EngineController {

	private final JmsTemplate jmsTemplate;
	private final String outgoingQueue;

	protected EngineControllerImpl(JmsTemplate jmsTemplate, String outgoingQueue) {
		this.jmsTemplate = jmsTemplate;
		this.outgoingQueue = outgoingQueue;
	}
	
	@Override
	public void updateExecutions() {
		sendMessage(new UpdateExecutionsCommandImpl());
	}

	private void sendMessage(Serializable data) {
		jmsTemplate.convertAndSend(outgoingQueue, data);
	}
}
