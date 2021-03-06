package cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web.client.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.models.SimulationConfiguration;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.data.SimulationStatus;

public class SimulationConfigurationItemImpl implements SimulationConfigurationItem {
	private int id;
	private Date created;
	private String creator;

	private String name;
	private String description;
	private int defaultRunMultiplicator;
	
	private final List<SimulationExecutionItem> executions = new ArrayList<>();
	private int dataId;
	private SimulationDataItem dataItem;
	private SimulationExecutionItem lastExecution;

	public SimulationConfigurationItemImpl() {
	}

	public SimulationConfigurationItemImpl(SimulationConfiguration configuration, SimulationDataItem dataItem, SimulationExecutionItem lastExecution) {
		this.id = configuration.getId();
		this.created = configuration.getCreated();
		this.creator = configuration.getCreator().getEmail();
		
		this.name = configuration.getName();
		this.description = configuration.getDescription();
		this.defaultRunMultiplicator = configuration.getDefaultRunMultiplicator();
		this.dataItem = dataItem;
		this.lastExecution = lastExecution;
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public void setId(int id) {
		this.id = id;
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
	public int getDefaultRunMultiplicator() {
		return defaultRunMultiplicator;
	}

	@Override
	public void setDefaultRunMultiplicator(int defaultRunMultiplicator) {
		this.defaultRunMultiplicator = defaultRunMultiplicator;
	}

	@Override
	public SimulationDataItem getData() {
		return dataItem;
	}

	@Override
	public int getDataId() {
		return dataId;
	}

	@Override
	public void setDataId(int dataId) {
		if (dataItem != null) {
			dataItem = null;
		}
		this.dataId = dataId;
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
		return lastExecution != null ? lastExecution.getCreated() : null;
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
