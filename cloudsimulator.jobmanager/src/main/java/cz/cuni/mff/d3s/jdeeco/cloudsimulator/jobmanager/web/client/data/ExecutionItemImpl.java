package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web.client.data;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.models.SimulationExecution;

public class ExecutionItemImpl implements ExecutionItem {

	private int id;
	
	public ExecutionItemImpl() {
	}
	
	public ExecutionItemImpl(SimulationExecution execution) {
		this.id = execution.getId();
	}

	@Override
	public int getId() {
		return id;
	}
}
