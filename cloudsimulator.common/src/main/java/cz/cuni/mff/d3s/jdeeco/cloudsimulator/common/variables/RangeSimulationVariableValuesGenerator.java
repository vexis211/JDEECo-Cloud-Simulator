package cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.variables;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.settings.RangeVariableSetting;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.settings.VariableSetting;

public class RangeSimulationVariableValuesGenerator implements SimulationVariableValuesGenerator<RangeVariableSetting> {

	private final Map<Class<?>, TypedRangeSimulationVariableValuesGenerator<?>> valueType2Generator;

	public RangeSimulationVariableValuesGenerator(
			List<TypedRangeSimulationVariableValuesGenerator<?>> typedGenerators) {
		this.valueType2Generator = typedGenerators.stream().collect(Collectors.toMap(x -> x.getValueType(), x -> x));
	}

	@Override
	public <TValue> List<TValue> generate(VariableSetting data, Class<TValue> valueClass) {

		return generate((RangeVariableSetting) data, RangeVariableSetting.class, valueClass);
	}

	@Override
	public <TValue> List<TValue> generate(RangeVariableSetting data, Class<RangeVariableSetting> dataClass,
			Class<TValue> valueClass) {

		@SuppressWarnings("unchecked")
		TypedRangeSimulationVariableValuesGenerator<TValue> generator = (TypedRangeSimulationVariableValuesGenerator<TValue>) valueType2Generator
				.get(valueClass);
		List<TValue> values = generator.generate(data);
		return values;
	}
}
