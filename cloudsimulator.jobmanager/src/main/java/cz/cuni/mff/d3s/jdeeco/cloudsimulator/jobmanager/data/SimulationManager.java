package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data;

import java.util.List;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.pack.PackagePreparedUpdate;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.updates.SimulationStatusUpdate;

public interface SimulationManager {
	
	List<SimulationExecutionEntry> listExecutions();
		
	void updateStatus(List<SimulationStatusUpdate> updates);
	void updatePackagePrepared(List<PackagePreparedUpdate> updates);
	void refreshExecutions();
}
