package cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.statistics;

import java.util.EnumSet;

public interface StatisticsConfiguration {
	EnumSet<StatisticsSaveMode> getSaveModes(String statisticId);
}
