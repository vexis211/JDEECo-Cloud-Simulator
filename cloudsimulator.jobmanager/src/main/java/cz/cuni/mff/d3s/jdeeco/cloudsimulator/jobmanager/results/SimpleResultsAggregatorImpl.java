package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.results;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.SimulationExecutionEntry;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.JobManagerUpdateQueue;

public class SimpleResultsAggregatorImpl implements SimpleResultsAggregator, ResultsAggregatorListener {

	private final ResultsAggregator resultsAggregator;
	private final JobManagerUpdateQueue jobManagerUpdateQueue;

	public SimpleResultsAggregatorImpl(ResultsAggregator resultsAggregator, JobManagerUpdateQueue jobManagerUpdateQueue) {
		this.resultsAggregator = resultsAggregator;
		this.jobManagerUpdateQueue = jobManagerUpdateQueue;
	}

	@Override
	public void aggregateResults(SimulationExecutionEntry execution) {
		resultsAggregator.aggregateResults(execution, this);
	}

	@Override
	public void resultsAggregated(int executionId) {
		jobManagerUpdateQueue.add(new ResultsAggregatedUpdateImpl(executionId));
	}
}
