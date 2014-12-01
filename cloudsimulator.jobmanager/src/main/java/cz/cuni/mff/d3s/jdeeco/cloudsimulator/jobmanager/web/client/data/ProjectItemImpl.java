package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web.client.data;

import java.util.ArrayList;
import java.util.List;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.models.Project;

public class ProjectItemImpl implements ProjectItem {
	private int id;
	private String name;
	private String description;
	private List<ConfigurationItem> configurations = new ArrayList<ConfigurationItem>();

	public ProjectItemImpl() {
	}

	public ProjectItemImpl(Project project) {
		this.id = project.getId();
		this.name = project.getDescription();
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int getFailingConfigurationsCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getSuccessfulConfigurationsCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<ConfigurationItem> getConfigurations() {
		return configurations;
	}

	@Override
	public void addConfiguration(ConfigurationItem configuration) {
		this.configurations.add(configuration);
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDescription(String description) {
		// TODO Auto-generated method stub

	}
}
