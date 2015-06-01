package cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.statistics.processors;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.statistics.StatisticsPersister;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.statistics.StatisticsSaveMode;

public class StatisticsAvgProcessor<T extends Number> implements StatisticsValueProcessor<T> {

	private double sum = 0d;
	private int count;

	@Override
	public void process(T value) {
		sum += value.doubleValue();
		count++;
	}

	@Override
	public void persist(StatisticsPersister persister) {
		persister.addScalarValue(StatisticsSaveMode.Avg, count != 0 ? sum / count : 0d);
	}
}
