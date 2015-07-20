package cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.statistics.processors;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.data.StatisticsSaveMode;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.statistics.StatisticsPersister;

public class StatisticsMinProcessor<T extends Comparable<T>> implements StatisticsValueProcessor<T> {

	private T minValue;
	private boolean isSet = false;

	@Override
	public void process(T value) {
		if (!isSet) {
			minValue = value;
		} else if (value.compareTo(minValue) < 0) {
			minValue = value;
		}
	}

	@Override
	public void persist(StatisticsPersister persister) {
		persister.addScalarValue(StatisticsSaveMode.Min, isSet ? minValue: null);
	}
}
