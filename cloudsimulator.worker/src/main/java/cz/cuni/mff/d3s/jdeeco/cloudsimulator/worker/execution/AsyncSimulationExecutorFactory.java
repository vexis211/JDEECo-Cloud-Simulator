package cz.cuni.mff.d3s.jdeeco.cloudsimulator.worker.execution;

public class AsyncSimulationExecutorFactory implements SimulationExecutorFactory{

	@Override
	public SimulationExecutor create(ExecutionListener listener, SimulationExecutorParameters parameters) {

		SimulationExecutor syncExecutor = new SimulationExecutorImpl(listener, parameters);
		SimulationExecutor asyncExecutor = new AsyncSimulationExecutorDecorator(syncExecutor);
		
		return asyncExecutor;
	}
		
}
