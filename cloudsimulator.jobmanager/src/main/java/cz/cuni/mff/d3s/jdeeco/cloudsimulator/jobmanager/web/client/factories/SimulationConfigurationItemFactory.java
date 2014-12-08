package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web.client.factories;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.models.SimulationConfiguration;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web.client.data.SimulationConfigurationItem;

public interface SimulationConfigurationItemFactory {

	public SimulationConfigurationItem create(SimulationConfiguration configuration, boolean addExecutions);
}
