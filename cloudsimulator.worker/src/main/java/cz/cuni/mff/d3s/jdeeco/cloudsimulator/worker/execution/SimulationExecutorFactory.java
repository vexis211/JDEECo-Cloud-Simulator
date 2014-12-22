package cz.cuni.mff.d3s.jdeeco.cloudsimulator.worker.execution;

public interface SimulationExecutorFactory {
		
	SimulationExecutor create(ExecutionListener listener, SimulationExecutorParameters parameters);
}
