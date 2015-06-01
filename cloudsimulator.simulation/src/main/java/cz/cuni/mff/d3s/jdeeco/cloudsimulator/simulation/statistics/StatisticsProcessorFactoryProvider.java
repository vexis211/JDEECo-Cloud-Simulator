package cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.statistics;

public interface StatisticsProcessorFactoryProvider {
	<T> StatisticsProcessorFactory<T> getGeneral(Class<T> clazz);
	<T extends Comparable<T>> StatisticsProcessorFactory<T> getForComparable(Class<T> clazz);
	<T extends Number> StatisticsProcessorFactory<T> getForNumber(Class<T> clazz);
}
