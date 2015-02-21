package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web.client.data;

import java.util.Date;
import java.util.List;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.SimulationStatus;

public interface SimulationConfigurationItem {

	public int getId();
	Date getCreated();
	String getCreator();

	public String getName();

	public void setName(String name);

	public String getDescription();

	public void setDescription(String description);

	public SimulationStatus getStatus();
	
	public String getStatusDesc();
	
	public Date getLastRunDate();

	public List<SimulationExecutionItem> getExecutions();	
	public void addExecution(SimulationExecutionItem execution);
}
