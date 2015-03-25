package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.planning;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.SimulationExecutionEntry;

public interface SimulationTimeEstimator {
	long estimateRunExecutionTimeInMillis(SimulationExecutionEntry execution);
}
