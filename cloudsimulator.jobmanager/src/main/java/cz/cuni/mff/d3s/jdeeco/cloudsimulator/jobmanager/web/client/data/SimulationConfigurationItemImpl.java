package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web.client.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.models.SimulationConfiguration;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.SimulationStatus;

public class SimulationConfigurationItemImpl implements SimulationConfigurationItem {
	private int id;
	private Date created;
	private String creator;

	private String name;
	private String description;
	private final List<SimulationExecutionItem> executions = new ArrayList<>();
	private SimulationExecutionItem lastExecution;

	public SimulationConfigurationItemImpl() {
	}

	public SimulationConfigurationItemImpl(SimulationConfiguration configuration, SimulationExecutionItem lastExecution) {
		this.id = configuration.getId();
		this.created = configuration.getCreated();
		this.creator = configuration.getCreator().getEmail();
		
		this.name = configuration.getName();
		this.description = configuration.getDescription();
		this.lastExecution = lastExecution;
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
	public String getDescription() {
		return description;
	}

	@Override
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public SimulationStatus getStatus() {
		return lastExecution != null ? lastExecution.getStatus() : SimulationStatus.None;
	}

	@Override
	public String getStatusDesc() {
		return lastExecution != null ? lastExecution.getStatusDesc() : null;
	}

	@Override
	public Date getLastRunDate() {
		return lastExecution != null ? lastExecution.getCreatedDate() : null;
	}

	@Override
	public List<SimulationExecutionItem> getExecutions() {
		return executions;
	}

	@Override
	public void addExecution(SimulationExecutionItem execution) {
		this.executions.add(execution);
	}
}
