package cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.statistics;

public class StatisticsProcessorFactoryProviderImpl implements StatisticsProcessorFactoryProvider {

	@Override
	public <T> StatisticsProcessorFactory<T> getGeneral(Class<T> clazz) {
		return new StatisticsProcessorGeneralFactoryImpl<T>(clazz);
	}

	@Override
	public <T extends Comparable<T>> StatisticsProcessorFactory<T> getForComparable(Class<T> clazz) {
		return new StatisticsProcessorComparableFactoryImpl<T>(clazz);
	}

	@Override
	public <T extends Number> StatisticsProcessorFactory<T> getForNumber(Class<T> clazz) {
		return new StatisticsProcessorNumberFactoryImpl<T>(clazz);
	}
}
