package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.pack;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.models.SimulationExecution;

public interface PackageManagerListener {
	void packagePrepared(SimulationExecution execution, String packageLocation);
}
