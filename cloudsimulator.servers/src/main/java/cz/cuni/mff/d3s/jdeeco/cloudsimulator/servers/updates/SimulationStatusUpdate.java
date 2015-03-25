package cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.updates;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.data.SimulationStatus;

public interface SimulationStatusUpdate extends WorkerUpdate {
	int getSimulationRunId();
	SimulationStatus getSimulationStatus();
	
	String getError();
}
