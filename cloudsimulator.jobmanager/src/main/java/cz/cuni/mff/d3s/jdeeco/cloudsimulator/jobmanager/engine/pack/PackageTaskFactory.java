package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.pack;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.models.SimulationExecution;

public interface PackageTaskFactory {
	PackageTask create(SimulationExecution execution);
}
