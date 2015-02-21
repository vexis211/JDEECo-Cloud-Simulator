package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web.client.data;

import java.util.Date;

public interface SimulationRunItem {

	int getId();

	String getStatus();
	
	Date getStartedDate();
	
	Date getEndedDate();
}
