package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.statistics;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class RunStatisticsFactoryImpl implements RunStatisticsFactory {

	@Override
	public RunStatistics create(int runId, RunStatistic[] statistics) {
		Map<String, RunStatistic> statisticMap = Arrays.stream(statistics).collect(Collectors.toMap(x -> x.getName(), x -> x));
		return new RunStatisticsImpl(runId, statisticMap);
	}
}