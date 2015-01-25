package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.data;

import java.util.List;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.updates.SimulationStatusUpdate;

public interface SimulationManager {
	
	List<SimulationExecutionEntry> listExecutions();
		
	void update(List<SimulationStatusUpdate> updates);

	void refreshExecutions();
}
