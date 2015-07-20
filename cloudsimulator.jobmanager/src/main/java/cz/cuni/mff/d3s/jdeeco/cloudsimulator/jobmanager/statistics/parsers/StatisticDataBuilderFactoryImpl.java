package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.statistics.parsers;

class StatisticDataBuilderFactoryImpl implements StatisticDataBuilderFactory {

	@Override
	public StatisticDataBuilder Create(StatisticDataBuilderListener listener) {
		return new StatisticsDataBuilderImpl(listener);
	}
}