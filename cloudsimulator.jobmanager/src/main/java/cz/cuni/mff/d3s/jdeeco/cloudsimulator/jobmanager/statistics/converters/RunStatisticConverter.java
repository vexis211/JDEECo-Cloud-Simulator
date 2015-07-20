package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.statistics.converters;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.statistics.RunStatistic;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.statistics.parsers.StatisticData;

public interface RunStatisticConverter {
	RunStatistic convert(StatisticData data);
}
