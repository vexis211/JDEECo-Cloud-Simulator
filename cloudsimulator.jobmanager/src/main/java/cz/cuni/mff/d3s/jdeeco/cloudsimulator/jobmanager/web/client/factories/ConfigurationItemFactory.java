package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web.client.factories;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.models.SimulationConfiguration;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web.client.data.ConfigurationItem;

public interface ConfigurationItemFactory {

	public ConfigurationItem create(SimulationConfiguration configuration, boolean addExecutions);
}
