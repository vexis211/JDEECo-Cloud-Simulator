package cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.statistics.processors;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.data.StatisticsSaveMode;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.statistics.StatisticsPersister;

public class StatisticsSumProcessor<T extends Number> implements StatisticsValueProcessor<T> {

	private double sum = 0d;

	@Override
	public void process(T value) {
		sum += value.doubleValue();
	}

	@Override
	public void persist(StatisticsPersister persister) {
		persister.addScalarValue(StatisticsSaveMode.Sum, sum);
	}
}
