package cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web.client.factories;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.data.models.SimulationConfiguration;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web.client.data.SimulationConfigurationItem;

public interface SimulationConfigurationItemFactory {

	public SimulationConfigurationItem create(SimulationConfiguration configuration, boolean addExecutions);
}
