package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.planning;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.data.SimulationExecutionEntry;

public interface SimulationTimeEstimator {
	long estimateRunExecutionTimeInMillis(SimulationExecutionEntry execution);
}
