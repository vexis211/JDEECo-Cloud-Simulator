package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web.client.data;

import java.util.Date;
import java.util.List;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.SimulationStatus;

public interface SimulationExecutionItem {

	int getId();

	String getDescription();

	void setDescription(String description);

	SimulationStatus getStatus();

	String getStatusDesc();
	
	Date getCreatedDate();
	
	Date getStartedDate();
	
	Date getEndedDate();

	List<SimulationRunItem> getRuns();	
	void addRun(SimulationRunItem run);
}
