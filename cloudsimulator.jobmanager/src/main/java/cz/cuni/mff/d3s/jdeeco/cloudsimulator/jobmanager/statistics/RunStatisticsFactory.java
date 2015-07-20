package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.statistics;

public interface RunStatisticsFactory {
	RunStatistics create(int runId, RunStatistic[] statistics);
}