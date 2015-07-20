package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.statistics.parsers;

interface StatisticDataBuilderFactory {
	StatisticDataBuilder Create(StatisticDataBuilderListener listener);
}