package cz.cuni.mff.d3s.jdeeco.cloudsimulator.worker.execution;

public interface ExecutionListener {
	
	void executionEnded(SimulationExecutor simulationExecutor);
	void executionStopped(SimulationExecutor simulationExecutor);
	void exceptionOccured(SimulationExecutor simulationExecutor, Exception e);
}
