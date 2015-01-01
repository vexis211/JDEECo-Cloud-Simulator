package cz.cuni.mff.d3s.jdeeco.cloudsimulator.worker.execution;

import javax.annotation.Resource;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.worker.FutureExecutor;

public class AsyncSimulationExecutorFactory implements SimulationExecutorFactory {

	@Resource
	private FutureExecutor futureExecutor;

	@Override
	public SimulationExecutor create(ExecutionListener listener, SimulationExecutorParameters parameters) {

		SimulationExecutor syncExecutor = new SimulationExecutorImpl(listener, parameters);
		SimulationExecutor asyncExecutor = new AsyncSimulationExecutorDecorator(syncExecutor, futureExecutor);

		return asyncExecutor;
	}

}
