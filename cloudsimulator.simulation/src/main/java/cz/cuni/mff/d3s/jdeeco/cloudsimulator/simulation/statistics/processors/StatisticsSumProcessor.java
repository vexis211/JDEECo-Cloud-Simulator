package cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.statistics.processors;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.statistics.StatisticsPersister;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.statistics.StatisticsSaveMode;

public class StatisticsSumProcessor<T extends Number> implements StatisticsValueProcessor<T> {

	private double sum = 0d;

	@Override
	public void process(T value) {
		sum += value.doubleValue();
	}

	@Override
	public void persist(StatisticsPersister<T> persister) {
		persister.persistScalarValue(StatisticsSaveMode.Sum, sum);
	}
}
