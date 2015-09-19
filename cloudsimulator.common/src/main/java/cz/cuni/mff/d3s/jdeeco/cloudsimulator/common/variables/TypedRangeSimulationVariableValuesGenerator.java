package cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.variables;

import java.util.List;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.settings.RangeVariableSetting;

public interface TypedRangeSimulationVariableValuesGenerator<TValue> {

	Class<TValue> getValueType();
	
	List<TValue> generate(RangeVariableSetting data);
}
