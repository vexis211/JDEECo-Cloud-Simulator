package cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.statistics;

public interface StatisticsProcessor<T> {
	String getStatisticId();
	
	void process(T value);
	void persist(StatisticsPersister persister);
}
