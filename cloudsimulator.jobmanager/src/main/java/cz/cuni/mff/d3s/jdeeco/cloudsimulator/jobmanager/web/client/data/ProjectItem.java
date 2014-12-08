package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web.client.data;

import java.util.List;

public interface ProjectItem {
	
	public static final String NAME_FIELD = "name";
	public static final String DESCRIPTION_FIELD = "description";
	

	public int getId();

	public String getName();
	public void setName(String name);

	public String getDescription();
	public void setDescription(String description);

	public List<SimulationConfigurationItem> getConfigurations();	
	public void addConfiguration(SimulationConfigurationItem configuration);	
	
	public int getFailingConfigurationsCount();
	public int getSuccessfulConfigurationsCount(); 

}