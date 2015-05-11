package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.results;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.SimulationExecutionEntry;

public interface SimpleResultsAggregator {
	void aggregateResults(SimulationExecutionEntry execution);
}
