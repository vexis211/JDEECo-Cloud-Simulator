package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web.client.data;

import java.util.List;

public interface ProjectItem {

	public int getId();

	public String getName();
	public void setName(String name);

	public String getDescription();
	public void setDescription(String description);

	public List<ConfigurationItem> getConfigurations();	
	public void addConfiguration(ConfigurationItem configuration);	
	
	public int getFailingConfigurationsCount();
	public int getSuccessfulConfigurationsCount(); 

}