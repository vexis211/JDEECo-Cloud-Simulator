package cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.variables;

import java.util.ArrayList;
import java.util.List;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.converters.TypeConverter;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.settings.ListVariableSetting;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.settings.VariableSetting;

public class ListSimulationVariableValuesGenerator implements SimulationVariableValuesGenerator<ListVariableSetting> {

	private final TypeConverter typeConverter;

	public ListSimulationVariableValuesGenerator(TypeConverter typeConverter) {
		this.typeConverter = typeConverter;
	}

	@Override
	public <TValue> List<TValue> generate(VariableSetting data, Class<TValue> valueClass) {
		return generate((ListVariableSetting) data, ListVariableSetting.class, valueClass);
	}

	@Override
	public <TValue> List<TValue> generate(ListVariableSetting data, Class<ListVariableSetting> dataClass,
			Class<TValue> valueClass) {

		List<TValue> values = new ArrayList<TValue>();

		String[] itemStrings = data.getList().split(String.valueOf(ListVariableSetting.ITEM_SEPARATOR));
		for (int i = 0; i < itemStrings.length; i++) {
			values.add(convert(itemStrings[i], valueClass));
		}

		return values;
	}

	private <TValue> TValue convert(String value, Class<TValue> valueClass) {
		return typeConverter.convert(value, valueClass);
	}
}
