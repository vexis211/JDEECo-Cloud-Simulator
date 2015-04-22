package cz.cuni.mff.d3s.jdeeco.cloudsimulator.worker.execution;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.FutureExecutor;

public class AsyncSimulationExecutorFactory implements SimulationExecutorFactory {

	private final FutureExecutor futureExecutor;
	private final String outputLogsFileName;

	public AsyncSimulationExecutorFactory(FutureExecutor futureExecutor, String outputLogsFileName) {
		this.futureExecutor = futureExecutor;
		this.outputLogsFileName = outputLogsFileName;
	}

	@Override
	public SimulationExecutor create(ExecutionListener listener, SimulationExecutorParameters parameters) {

		SimulationExecutor syncExecutor = new SimulationExecutorImpl(listener, parameters, outputLogsFileName);
		SimulationExecutor asyncExecutor = new AsyncSimulationExecutorDecorator(syncExecutor, futureExecutor);

		return asyncExecutor;
	}

}
