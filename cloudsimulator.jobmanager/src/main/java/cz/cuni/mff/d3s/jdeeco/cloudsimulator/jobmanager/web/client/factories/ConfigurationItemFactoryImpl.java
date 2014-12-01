package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web.client.factories;

import javax.annotation.Resource;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.models.SimulationConfiguration;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web.client.data.ConfigurationItem;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web.client.data.ConfigurationItemImpl;

public class ConfigurationItemFactoryImpl implements ConfigurationItemFactory {

	@Resource
	private ExecutionItemFactory executionItemFactory;

	@Override
	public ConfigurationItem create(SimulationConfiguration configuration, boolean addExecutions) {
		ConfigurationItem newConfigItem = new ConfigurationItemImpl(configuration);
		
		if (addExecutions) {
			configuration.getSimulationExecutions().stream().sorted((c1, c2) -> c1.getId().compareTo(c2.getId()))
					.forEach(c -> newConfigItem.addExecution(executionItemFactory.create(c, false)));
		}
		
		return newConfigItem;
	}

}
