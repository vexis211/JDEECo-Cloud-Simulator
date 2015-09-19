package cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.variables;

import java.util.List;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.settings.VariableSetting;

public interface SimulationVariableValuesProvider {
	<TValue> List<TValue> get(VariableSetting dataSettings, Class<TValue> valueClass);
}
