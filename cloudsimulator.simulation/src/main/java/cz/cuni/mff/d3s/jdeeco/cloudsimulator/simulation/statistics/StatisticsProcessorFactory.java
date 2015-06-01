package cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.statistics;

import java.util.EnumSet;

public interface StatisticsProcessorFactory<T> {
	StatisticsProcessor<T> create(String statisticId, EnumSet<StatisticsSaveMode> saveMode);
}
