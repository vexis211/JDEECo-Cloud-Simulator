package cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.variables;

import java.util.List;
import java.util.Map;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.settings.VariableSetting;

public class SimulationVariableValuesProviderImpl implements SimulationVariableValuesProvider {

	private final Map<Class<?>, SimulationVariableValuesGenerator<? extends VariableSetting>> generators;

	public SimulationVariableValuesProviderImpl(
			Map<Class<?>, SimulationVariableValuesGenerator<? extends VariableSetting>> generators) {
		this.generators = generators;
	}

	@Override
	public <TValue> List<TValue> get(VariableSetting dataSettings, Class<TValue> valueClass) {
		SimulationVariableValuesGenerator<?> generator = generators.get(dataSettings.getClass());
		
		return generator.generate(dataSettings, valueClass);
	}
}
