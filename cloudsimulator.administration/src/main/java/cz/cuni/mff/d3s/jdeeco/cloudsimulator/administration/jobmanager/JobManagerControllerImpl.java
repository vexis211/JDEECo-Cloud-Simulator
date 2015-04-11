package cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.jobmanager;

import java.io.Serializable;

import org.springframework.jms.core.JmsTemplate;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.commands.UpdateExecutionsCommandImpl;

public class JobManagerControllerImpl implements JobManagerController {

	private final JmsTemplate jmsTemplate;
	private final String jobManagerControlQueue;

	protected JobManagerControllerImpl(JmsTemplate jmsTemplate, String jobManagerControlQueue) {
		this.jmsTemplate = jmsTemplate;
		this.jobManagerControlQueue = jobManagerControlQueue;
	}
	
	@Override
	public void updateExecutions() {
		sendMessage(new UpdateExecutionsCommandImpl());
	}

	private void sendMessage(Serializable data) {
		jmsTemplate.convertAndSend(jobManagerControlQueue, data);
	}
}
