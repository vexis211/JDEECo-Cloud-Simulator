package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.pack;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.models.SimulationExecution;

public interface PackageManager {
	String getPackageLocation(SimulationExecution execution);
	void preparePackage(SimulationExecution execution, PackageManagerListener listener);
}
