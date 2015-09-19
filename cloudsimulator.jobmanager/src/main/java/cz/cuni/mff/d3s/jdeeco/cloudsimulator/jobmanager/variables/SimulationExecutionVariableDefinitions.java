package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.variables;

import java.util.List;

public interface SimulationExecutionVariableDefinitions {
	List<SimulationExecutionVariableDefinition> getDefinitions();
	List<SimulationRunVariables> getPossibleRunCombinations();
}
