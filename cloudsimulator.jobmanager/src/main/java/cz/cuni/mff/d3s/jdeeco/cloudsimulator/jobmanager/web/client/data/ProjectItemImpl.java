package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web.client.data;

import java.util.ArrayList;
import java.util.List;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.models.Project;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.SimulationStatus;

public class ProjectItemImpl implements ProjectItem {
	private int id;
	private String name;
	private String description;
	private List<SimulationConfigurationItem> configurations = new ArrayList<SimulationConfigurationItem>();

	public ProjectItemImpl() {
	}

	public ProjectItemImpl(Project project) {
		this.id = project.getId();
		this.name = project.getName();
		this.description = project.getDescription();
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
		return (int) configurations.stream().filter(x -> x.getStatus() == SimulationStatus.ErrorOccured).count();
	}

	@Override
	public int getSuccessfulConfigurationsCount() {
		return (int) configurations.stream().filter(x -> x.getStatus() == SimulationStatus.Completed).count();
	}

	@Override
	public List<SimulationConfigurationItem> getConfigurations() {
		return configurations;
	}

	@Override
	public void addConfiguration(SimulationConfigurationItem configuration) {
		this.configurations.add(configuration);
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public void setDescription(String description) {
		this.description = description;
	}
}
