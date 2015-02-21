package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web.client.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.models.Project;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.SimulationStatus;

public class ProjectItemImpl implements ProjectItem {
	private int id;
	private Date created;
	private String creator;
	
	private String name;
	private String description;
	private List<SimulationConfigurationItem> configurations = new ArrayList<SimulationConfigurationItem>();
	private List<SimulationDataItem> datas = new ArrayList<>();

	public ProjectItemImpl() {
	}

	public ProjectItemImpl(Project project) {
		this.id = project.getId();
		this.created = project.getCreated();
		this.creator = project.getCreator().getEmail();
		
		this.name = project.getName();
		this.description = project.getDescription();
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public Date getCreated() {
		return created;
	}

	@Override
	public String getCreator() {
		return creator;
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

	@Override
	public List<SimulationDataItem> getDatas() {
		return datas;
	}

	@Override
	public void addData(SimulationDataItem data) {
		this.datas.add(data);
	}
}
