package cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.statistics.processors;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.data.StatisticsSaveMode;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.statistics.StatisticsPersister;

public class StatisticsCountProcessor<T> implements StatisticsValueProcessor<T> {

	private long count;

	@Override
	public void process(T value) {
		count++;
//		System.out.println("Stat +1 => " + count);
	}


	@Override
	public void persist(StatisticsPersister persister) {
		persister.addScalarValue(StatisticsSaveMode.Count, count);
	}
}
