package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.results;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.SimulationExecutionEntry;

public class ResultsAggregatorImpl implements ResultsAggregator {

	@Override
	public void aggregateResults(SimulationExecutionEntry execution, ResultsAggregatorListener listener) {
		// TODO Auto-generated method stub
		listener.resultsAggregated(execution.getId());
	}

}
