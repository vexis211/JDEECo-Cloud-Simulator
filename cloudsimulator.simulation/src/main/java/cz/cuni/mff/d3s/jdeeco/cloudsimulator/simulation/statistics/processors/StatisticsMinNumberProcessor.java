package cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.statistics.processors;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.statistics.StatisticsPersister;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.statistics.StatisticsSaveMode;

public class StatisticsMinNumberProcessor<T extends Number> implements StatisticsValueProcessor<T> {

	private T minValue;
	private boolean isSet = false;

	@Override
	public void process(T value) {
		if (!isSet) {
			minValue = value;
		} else if (value.doubleValue() < minValue.doubleValue()) {
			minValue = value;
		}
	}

	@Override
	public void persist(StatisticsPersister persister) {
		persister.addScalarValue(StatisticsSaveMode.Min, isSet ? minValue: null);
	}
}
