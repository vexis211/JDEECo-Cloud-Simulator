package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.statistics;

import java.util.Map;

public class RunStatisticsImpl implements RunStatistics {

	private final int runId;
	private final Map<String, RunStatistic> statistics;

	public RunStatisticsImpl(int runId, Map<String, RunStatistic> statistics) {
		this.runId = runId;
		this.statistics = statistics;
	}

	@Override
	public int getRunId() {
		return runId;
	}

	@Override
	public Map<String, RunStatistic> getStatistics() {
		return statistics;
	}

	@Override
	public void accept(RunStatisticVisitor visitor) {
		for (RunStatistic statistic : statistics.values()) {
			statistic.accept(visitor);
		}
	}
}