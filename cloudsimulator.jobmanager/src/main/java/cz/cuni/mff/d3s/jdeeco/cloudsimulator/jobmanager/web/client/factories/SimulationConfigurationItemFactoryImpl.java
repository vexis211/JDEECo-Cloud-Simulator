package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web.client.factories;

import javax.annotation.Resource;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.models.SimulationConfiguration;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.models.SimulationExecution;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web.client.data.SimulationConfigurationItem;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web.client.data.SimulationConfigurationItemImpl;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web.client.data.SimulationExecutionItem;

public class SimulationConfigurationItemFactoryImpl implements SimulationConfigurationItemFactory {

	@Resource
	private SimulationExecutionItemFactory executionItemFactory;

	@Override
	public SimulationConfigurationItem create(SimulationConfiguration configuration, boolean addExecutions) {
		
		SimulationExecution lastExecution = configuration.getSimulationExecutions().stream()
				.max((e1, e2) -> e1.getId().compareTo(e2.getId())).get();
		SimulationExecutionItem lastExecutionItem = lastExecution != null ? executionItemFactory.create(lastExecution, false) : null;
		
		SimulationConfigurationItem newConfigItem = new SimulationConfigurationItemImpl(configuration, lastExecutionItem);
		
		if (addExecutions) {
			configuration.getSimulationExecutions().stream().sorted((c1, c2) -> c1.getId().compareTo(c2.getId()))
					.forEach(c -> newConfigItem.addExecution(executionItemFactory.create(c, false)));
		}
		
		return newConfigItem;
	}
}
