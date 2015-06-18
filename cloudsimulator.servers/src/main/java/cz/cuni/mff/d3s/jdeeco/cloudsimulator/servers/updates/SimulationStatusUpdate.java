package cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.updates;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.data.SimulationExitReason;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.data.SimulationStatus;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.SimulationId;

public interface SimulationStatusUpdate extends WorkerUpdate {
	SimulationId getSimulationId();
	SimulationStatus getSimulationStatus();	
	SimulationExitReason getExitReason();
	String getError();
}
