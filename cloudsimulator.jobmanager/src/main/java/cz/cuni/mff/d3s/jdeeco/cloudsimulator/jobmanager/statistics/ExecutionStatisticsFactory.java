package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.statistics;

public interface ExecutionStatisticsFactory {
	ExecutionStatistics create(int executionId, RunStatistics[] runStatistics);
}