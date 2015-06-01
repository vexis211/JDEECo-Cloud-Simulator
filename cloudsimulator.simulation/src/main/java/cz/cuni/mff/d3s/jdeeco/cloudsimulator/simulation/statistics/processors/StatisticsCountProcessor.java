package cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.statistics.processors;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.statistics.StatisticsPersister;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.statistics.StatisticsSaveMode;

public class StatisticsCountProcessor<T> implements StatisticsValueProcessor<T> {

	private int count;

	@Override
	public void process(T value) {
		count++;
	}


	@Override
	public void persist(StatisticsPersister<T> persister) {
		persister.persistScalarValue(StatisticsSaveMode.Count, count);
	}
}
