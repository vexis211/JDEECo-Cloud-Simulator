package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.statistics.parsers;

import java.util.HashMap;
import java.util.Map;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.data.StatisticsSaveMode;

public class StatisticData {
	private final String name;
	private Class<?> valueClass;
	private Map<StatisticsSaveMode, String> values = new HashMap<>();

	public StatisticData(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public Class<?> getValueClass() {
		return valueClass;
	}

	public void setValueClass(Class<?> valueClass) {
		this.valueClass = valueClass;
	}

	public Map<StatisticsSaveMode, String> getValues() {
		return values;
	}

	public void setValue(StatisticsSaveMode saveMode, String value) {
		this.values.put(saveMode, value);
	}
}
