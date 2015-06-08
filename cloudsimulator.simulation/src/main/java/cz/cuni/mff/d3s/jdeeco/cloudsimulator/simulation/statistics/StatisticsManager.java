package cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.statistics;


public interface StatisticsManager {
	<T> StatisticsProcessor<T> getGeneralProcessor(String statisticId, Class<T> clazz);
	<T extends Comparable<T>> StatisticsProcessor<T> getComparableProcessor(String statisticId, Class<T> clazz);
	<T extends Number> StatisticsProcessor<T> getNumberProcessor(String statisticId, Class<T> clazz);
	
	void persistStatistics();
}
