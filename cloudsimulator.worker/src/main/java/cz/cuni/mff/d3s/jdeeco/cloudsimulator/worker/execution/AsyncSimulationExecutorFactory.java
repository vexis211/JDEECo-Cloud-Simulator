package cz.cuni.mff.d3s.jdeeco.cloudsimulator.worker.execution;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.worker.FutureExecutor;

public class AsyncSimulationExecutorFactory implements SimulationExecutorFactory {

	private final FutureExecutor futureExecutor;

	public AsyncSimulationExecutorFactory(FutureExecutor futureExecutor) {
		this.futureExecutor = futureExecutor;
	}

	@Override
	public SimulationExecutor create(ExecutionListener listener, SimulationExecutorParameters parameters) {

		SimulationExecutor syncExecutor = new SimulationExecutorImpl(listener, parameters);
		SimulationExecutor asyncExecutor = new AsyncSimulationExecutorDecorator(syncExecutor, futureExecutor);

		return asyncExecutor;
	}

}
