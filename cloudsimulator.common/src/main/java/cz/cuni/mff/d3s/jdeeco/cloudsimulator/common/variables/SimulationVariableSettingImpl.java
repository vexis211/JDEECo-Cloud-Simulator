package cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.variables;

import java.util.List;

public class SimulationVariableSettingImpl<T> implements SimulationVariableSetting {

	private String name;
	private Class<T> valueClass;
	private List<T> values;

	public SimulationVariableSettingImpl(String name, Class<T> valueClass, List<T> values) {
		super();
		this.name = name;
		this.valueClass = valueClass;
		this.values = values;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Class<?> getValueClass() {
		return valueClass;
	}

	@Override
	public void accept(SimulationVariableSettingVisitor visitor) {
		visitor.visit(name, valueClass, values);
	}
}
