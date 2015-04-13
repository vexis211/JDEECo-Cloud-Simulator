package cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web.client.data;

import java.util.Date;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.data.SimulationStatus;

public interface SimulationRunItem {

	int getId();

	SimulationStatus getStatus();
	
	Date getStartedDate();
	Date getEndedDate();
}
