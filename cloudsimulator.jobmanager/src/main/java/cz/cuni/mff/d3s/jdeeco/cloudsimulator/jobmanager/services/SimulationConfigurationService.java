package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.services;

import java.util.List;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.models.SimulationConfiguration;;

public interface SimulationConfigurationService {
	List<SimulationConfiguration> listConfigurations();
	List<SimulationConfiguration> listProjectConfigurations(int projectId);

	SimulationConfiguration getConfigurationById(int configurationId);
	
	SimulationConfiguration createConfiguration(int projectId, int dataId, String name, String description, int defaultRunCount);
	void editConfiguration(int configurationId, String name, String description, int defaultRunCount);
}
