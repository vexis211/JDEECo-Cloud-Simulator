package cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.variables;

import java.util.List;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.settings.VariableSetting;

public interface SimulationVariableValuesGenerator<TData extends VariableSetting> {
	<TValue> List<TValue> generate(VariableSetting data, Class<TValue> valueClass);
	
	<TValue> List<TValue> generate(TData data, Class<TData> dataClass, Class<TValue> valueClass);
}
