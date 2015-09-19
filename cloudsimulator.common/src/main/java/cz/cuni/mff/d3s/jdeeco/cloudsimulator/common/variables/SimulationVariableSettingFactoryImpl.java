package cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.variables;

import java.util.List;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.converters.String2ClassConverter;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.settings.VariableSetting;

class SimulationVariableSettingFactoryImpl implements SimulationVariableSettingFactory {

	private final String2ClassConverter type2StringConverter;
	private final SimulationVariableValuesProvider simulationVariableValuesProvider;

	public SimulationVariableSettingFactoryImpl(String2ClassConverter type2StringConverter,
			SimulationVariableValuesProvider simulationVariableValuesProvider) {
		this.type2StringConverter = type2StringConverter;
		this.simulationVariableValuesProvider = simulationVariableValuesProvider;
	}

	@Override
	public SimulationVariableSetting create(VariableSetting data) {
		Class<?> valueClass = type2StringConverter.convert(data.getValueType());

		return create(data, valueClass);
	}

	private <T> SimulationVariableSetting create(VariableSetting data, Class<T> valueClass) {
		List<T> values = simulationVariableValuesProvider.get(data, valueClass);
		return new SimulationVariableSettingImpl<T>(data.getName(), valueClass, values);
	}
}