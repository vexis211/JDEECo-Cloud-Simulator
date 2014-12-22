package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web.client.data;

import java.util.Date;

public interface SimulationRunItem {

	public int getId();

	public String getStatus();
	
	public Date getStartedDate();
	
	public Date getEndedDate();
}
