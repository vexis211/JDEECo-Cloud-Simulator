package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.statistics;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.extensions.PathEx;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.SimulationExecutionEntry;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.SimulationRunEntry;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.statistics.parsers.RunStatisticsParser;

// Provides what cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.StatisticsPersisterImpl persists
public class StatisticsProviderImpl implements StatisticsProvider {

	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(StatisticsProviderImpl.class);

	private final String runStatisticsRoot;
	private final RunStatisticsParser runStatisticsParser;
	private final RunStatisticsFactory runStatisticsFactory;
	private final ExecutionStatisticsFactory executionStatisticsFactory;

	public StatisticsProviderImpl(String runStatisticsRoot, RunStatisticsParser runStatisticsParser,
			RunStatisticsFactory runStatisticsFactory, ExecutionStatisticsFactory executionStatisticsFactory) {
		this.runStatisticsRoot = runStatisticsRoot;
		this.runStatisticsParser = runStatisticsParser;
		this.runStatisticsFactory = runStatisticsFactory;
		this.executionStatisticsFactory = executionStatisticsFactory;
	}

	@Override
	public ExecutionStatistics getStatistics(SimulationExecutionEntry execution) {
		List<SimulationRunEntry> completedRuns = execution.getCompletedRuns();
		int runCount = completedRuns.size();
		RunStatistics[] runStatisticsList = new RunStatistics[runCount];

		for (int i = 0; i < runCount; i++) {
			SimulationRunEntry simulationRunEntry = completedRuns.get(i);
			RunStatistics runStatistics = getStatistics(simulationRunEntry);
			runStatisticsList[i] = runStatistics;
		}

		return executionStatisticsFactory.create(execution.getId(), runStatisticsList);
	}

	@Override
	public RunStatistics getStatistics(SimulationRunEntry run) {
		String statisticsDirPath = PathEx.combine(runStatisticsRoot, run.getId());
		File statisticsDir = new File(statisticsDirPath);

		List<RunStatistic> allStatistics = new ArrayList<>();
		for (String statisticsFilePath : statisticsDir.list()) {
			RunStatistic[] statistics = runStatisticsParser.parse(statisticsFilePath);
			for (RunStatistic statistic : statistics) {
				allStatistics.add(statistic);
			}
		}

		RunStatistic[] allStatisticsArray = allStatistics.toArray(new RunStatistic[allStatistics.size()]);
		return runStatisticsFactory.create(run.getId(), allStatisticsArray);
	}
}
