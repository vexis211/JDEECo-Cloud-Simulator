package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web.client.factories;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.models.SimulationExecution;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web.client.data.SimulationExecutionItem;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web.client.data.SimulationExecutionItemImpl;

public class SimulationExecutionItemFactoryImpl implements SimulationExecutionItemFactory {

	@Override
	public SimulationExecutionItem create(SimulationExecution execution) {
		SimulationExecutionItem newExecutionItem = new SimulationExecutionItemImpl(execution);
		return newExecutionItem;
	}

}
