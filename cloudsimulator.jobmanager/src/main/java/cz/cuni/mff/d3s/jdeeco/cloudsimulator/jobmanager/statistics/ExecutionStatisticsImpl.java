package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.statistics;

class ExecutionStatisticsImpl implements ExecutionStatistics {

	private final int executionId;
	private final RunStatistics[] runStatistics;
	
	public ExecutionStatisticsImpl(int executionId, RunStatistics[] runStatistics) {
		this.executionId = executionId;
		this.runStatistics = runStatistics;
	}

	@Override
	public int getExecutionId() {
		return executionId;
	}

	@Override
	public RunStatistics[] getRunStatistics() {
		return runStatistics;
	}
}