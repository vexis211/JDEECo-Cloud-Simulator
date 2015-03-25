package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.data;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.data.SimulationStatus;

public interface SimulationRunEntry {
	
	int getId();
	SimulationExecutionEntry getExecution();
	
	SimulationStatus getStatus();
	void setStatus(SimulationStatus status);
}
