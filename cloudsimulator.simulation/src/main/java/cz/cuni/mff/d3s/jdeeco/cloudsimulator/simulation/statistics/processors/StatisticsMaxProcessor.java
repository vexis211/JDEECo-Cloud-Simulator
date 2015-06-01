package cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.statistics.processors;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.statistics.StatisticsPersister;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.statistics.StatisticsSaveMode;

public class StatisticsMaxProcessor<T extends Comparable<T>> implements StatisticsValueProcessor<T> {

	private T maxValue;
	private boolean isSet = false;

	@Override
	public void process(T value) {
		if (!isSet) {
			maxValue = value;
		} else if (value.compareTo(maxValue) > 0) {
			maxValue = value;
		}
	}

	@Override
	public void persist(StatisticsPersister<T> persister) {
		persister.persistScalarValue(StatisticsSaveMode.Max, isSet ? maxValue : null);
	}
}
