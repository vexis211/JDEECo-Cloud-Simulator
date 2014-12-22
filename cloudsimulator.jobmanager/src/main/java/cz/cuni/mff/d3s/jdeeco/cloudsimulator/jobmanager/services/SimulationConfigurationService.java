package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.services;

import java.util.List;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.models.SimulationConfiguration;;

public interface SimulationConfigurationService {
	List<SimulationConfiguration> listConfigurations();
	List<SimulationConfiguration> listVisibleConfigurations();
	List<SimulationConfiguration> listHiddenConfigurations();
	List<SimulationConfiguration> listVisibleConfigurations(int projectId);
	List<SimulationConfiguration> listHiddenConfigurations(int projectId);

	SimulationConfiguration getConfigurationById(int configurationId);
	
	SimulationConfiguration createConfiguration(int projectId, String name, String description);
	void editConfiguration(int configurationId, String name, String description);
}
