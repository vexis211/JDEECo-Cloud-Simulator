package cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web.client.data;

import java.util.Date;
import java.util.List;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.data.SimulationStatus;

public interface SimulationConfigurationItem {

	int getId();
	void setId(int id);
	
	Date getCreated();
	String getCreator();
	
	
	String getName();
	void setName(String name);

	String getDescription();
	void setDescription(String description);

	int getDefaultRunCount();
	void setDefaultRunCount(int defaultRunCount);

	SimulationDataItem getData();

	int getDataId();
	void setDataId(int dataId);
	
	SimulationStatus getStatus();
	String getStatusDesc();
	
	Date getLastRunDate();
	
	List<SimulationExecutionItem> getExecutions();	
	void addExecution(SimulationExecutionItem execution);
}
