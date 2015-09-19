package cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.variables;

public interface SimulationVariableSetting {

	String getName();

	Class<?> getValueClass();

	void accept(SimulationVariableSettingVisitor visitor);
}
