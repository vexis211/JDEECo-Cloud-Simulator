package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.data;

import java.util.List;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.pack.PackagePreparedUpdate;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.updates.SimulationStatusUpdate;

public interface SimulationManager {
	
	List<SimulationExecutionEntry> listExecutions();
		
	void updateStatus(List<SimulationStatusUpdate> updates);
	void updatePackageNames(List<PackagePreparedUpdate> updates);
	void refreshExecutions();
}
