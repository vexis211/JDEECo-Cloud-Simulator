package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.statistics.parsers;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.statistics.RunStatistic;

public interface RunStatisticsParser {
	RunStatistic[] parse(String filePath);
}
