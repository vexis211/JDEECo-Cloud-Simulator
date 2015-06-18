package cz.cuni.mff.d3s.jdeeco.cloudsimulator.worker.execution;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.data.SimulationExitReason;

public interface ExecutionListener {
	
	void executionEnded(SimulationExecutor simulationExecutor, SimulationExitReason exitReason);
	void executionStopped(SimulationExecutor simulationExecutor, SimulationExitReason exitReason);
	void exceptionOccured(SimulationExecutor simulationExecutor, Exception e);
}
