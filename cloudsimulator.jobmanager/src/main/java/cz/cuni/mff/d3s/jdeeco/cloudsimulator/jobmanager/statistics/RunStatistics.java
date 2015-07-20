package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.statistics;

import java.util.Map;

public interface RunStatistics {
	int getRunId();
	Map<String, RunStatistic> getStatistics();

	void accept(RunStatisticVisitor visitor);
}