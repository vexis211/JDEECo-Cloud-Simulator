package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.planning;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.SimulationExecutionEntry;

public class SimulationTimeEstimatorImpl implements SimulationTimeEstimator {

	@Override
	public long estimateRunExecutionTimeInMillis(SimulationExecutionEntry execution) {
		
		if (execution.getStatistics().getCompletedSimulationsCount() > 0) {
			return execution.getStatistics().getAverageSimulationTimeInMillis();
		}
		
		return  1000 * 60 * 5; // TODO implement
	}

}
