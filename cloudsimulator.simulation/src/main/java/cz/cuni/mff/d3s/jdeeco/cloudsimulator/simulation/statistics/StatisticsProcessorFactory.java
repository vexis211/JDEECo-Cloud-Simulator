package cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.statistics;

import java.util.EnumSet;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.data.StatisticsSaveMode;

public interface StatisticsProcessorFactory<T> {
	StatisticsProcessor<T> create(String statisticId, EnumSet<StatisticsSaveMode> saveMode);
}
