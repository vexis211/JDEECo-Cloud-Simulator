package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web.client.data;

import java.util.List;

public interface ProjectItem {
	int getId();

	String getName();
	void setName(String name);

	String getDescription();
	void setDescription(String description);

	List<SimulationConfigurationItem> getConfigurations();	
	void addConfiguration(SimulationConfigurationItem configuration);	
	
	int getFailingConfigurationsCount();
	int getSuccessfulConfigurationsCount(); 

	List<SimulationDataItem> getDatas();	
	void addData(SimulationDataItem data);
}