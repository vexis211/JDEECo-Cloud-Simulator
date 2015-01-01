package cz.cuni.mff.d3s.jdeeco.cloudsimulator.worker.execution;

public interface SimulationExecutor {
	
	SimulationExecutorParameters getParameters();
	
	void start();
	void stop();
}
