package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.pack;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.variables.SimulationExecutionVariableDefinitions;

public interface PackageManagerListener {
	void packagePrepared(int executionId, SimulationExecutionVariableDefinitions variablesDefinition);
}
