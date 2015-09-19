package cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.variables;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.settings.VariableSetting;

public interface SimulationVariableSettingFactory {
	SimulationVariableSetting create(VariableSetting data);
}
