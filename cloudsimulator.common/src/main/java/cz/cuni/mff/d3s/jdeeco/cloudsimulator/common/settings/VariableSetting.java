package cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.settings;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

public class VariableSetting {

	@XStreamAlias("Name")
	@XStreamAsAttribute
	private final String name;

	@XStreamAlias("Type")
	@XStreamAsAttribute
	private final String valueType;

	public VariableSetting(String name, String valueType) {
		this.name = name;
		this.valueType = valueType;
	}

	public String getName() {
		return name;
	}

	public String getValueType() {
		return valueType;
	}
}
