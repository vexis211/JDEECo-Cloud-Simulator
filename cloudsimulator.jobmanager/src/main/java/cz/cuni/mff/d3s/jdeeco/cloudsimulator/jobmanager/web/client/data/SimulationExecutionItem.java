package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web.client.data;

import java.util.Date;
import java.util.List;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.models.SimulationStatus;

public interface SimulationExecutionItem {

	public int getId();

	public String getDescription();

	public void setDescription(String description);

	public SimulationStatus getStatus();

	public String getStatusDesc();
	
	public Date getCreatedDate();
	
	public Date getStartedDate();
	
	public Date getEndedDate();

	public List<SimulationRunItem> getRuns();	
	public void addRun(SimulationRunItem run);
}
