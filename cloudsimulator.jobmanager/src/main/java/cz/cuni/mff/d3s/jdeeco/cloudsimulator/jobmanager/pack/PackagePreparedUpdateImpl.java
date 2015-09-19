package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.pack;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.variables.SimulationExecutionVariableDefinitions;

public class PackagePreparedUpdateImpl implements PackagePreparedUpdate {

	private static final long serialVersionUID = -4205471535823206102L;

	private final int executionId;
	private final SimulationExecutionVariableDefinitions variablesDefinition;

	public PackagePreparedUpdateImpl(int executionId, SimulationExecutionVariableDefinitions variablesDefinition) {
		this.executionId = executionId;
		this.variablesDefinition = variablesDefinition;
	}

	@Override
	public int getExecutionId() {
		return executionId;
	}

	@Override
	public SimulationExecutionVariableDefinitions getVariablesDefinitions() {
		return variablesDefinition;
	}
}
