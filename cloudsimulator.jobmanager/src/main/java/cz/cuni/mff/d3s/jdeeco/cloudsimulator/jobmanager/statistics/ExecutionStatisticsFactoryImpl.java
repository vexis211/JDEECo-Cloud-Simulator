package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.statistics;

class ExecutionStatisticsFactoryImpl implements ExecutionStatisticsFactory {

	@Override
	public ExecutionStatistics create(int executionId, RunStatistics[] runStatistics) {
		return new ExecutionStatisticsImpl(executionId, runStatistics);
	}
}