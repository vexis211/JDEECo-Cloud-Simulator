package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web.client.factories;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.models.SimulationExecution;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web.client.data.ExecutionItem;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web.client.data.ExecutionItemImpl;

public class ExecutionItemFactory {

	public ExecutionItem create(SimulationExecution execution, boolean addRuns) {
		ExecutionItem newExecutionItem = new ExecutionItemImpl(execution);
		return newExecutionItem;
	}

}
