package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web.client.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.models.SimulationConfiguration;

public class ConfigurationItemImpl implements ConfigurationItem {
	private int id;
	private String name;
	private String status;
	private Date lastRunDate;
	private List<ExecutionItem> executions = new ArrayList<ExecutionItem>();
	
	public ConfigurationItemImpl() {
	}
	
	public ConfigurationItemImpl(SimulationConfiguration configuration) {
		this.id = configuration.getId();
		this.name = configuration.getName();
		// TODO implement
		this.status = "TODO";
		this.lastRunDate = new Date();
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
	public String getStatus() {
		return status;
	}

	@Override
	public Date getLastRunDate() {
		return lastRunDate;
	}

	@Override
	public List<ExecutionItem> getExecutions() {
		return executions;
	}

	@Override
	public void addExecution(ExecutionItem execution) {
		this.executions.add(execution);
	}
}
