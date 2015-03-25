package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.pack;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.models.SimulationExecution;

public interface PackageManager {
	String getPackageName(SimulationExecution execution);
	void preparePackage(SimulationExecution execution, PackageManagerListener listener);
}
