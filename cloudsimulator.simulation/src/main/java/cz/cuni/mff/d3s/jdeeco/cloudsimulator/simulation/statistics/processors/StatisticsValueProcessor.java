package cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.statistics.processors;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.statistics.StatisticsPersister;

public interface StatisticsValueProcessor<T> {
	void process(T value);
	
	void persist(StatisticsPersister<T> persister);
}
