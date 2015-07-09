package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.results;

import java.util.List;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.SimulationExecutionEntry;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.SimulationRunEntry;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.results.providers.StatisticsProvider;

public class ResultsAggregatorImpl implements ResultsAggregator {

	private final StatisticsProvider statisticsProvider;

	public ResultsAggregatorImpl(StatisticsProvider statisticsProvider) {
		this.statisticsProvider = statisticsProvider;
	}
	
	@Override
	public void aggregateResults(SimulationExecutionEntry execution, ResultsAggregatorListener listener) {
		List<SimulationRunEntry> completedRuns = execution.getCompletedRuns();
		
		
		// TODO Auto-generated method stub
		listener.resultsAggregated(execution.getId());
	}
}
