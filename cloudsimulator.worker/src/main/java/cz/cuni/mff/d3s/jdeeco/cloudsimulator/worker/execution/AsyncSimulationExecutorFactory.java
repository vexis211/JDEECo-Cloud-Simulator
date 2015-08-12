package cz.cuni.mff.d3s.jdeeco.cloudsimulator.worker.execution;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.FutureExecutor;

public class AsyncSimulationExecutorFactory implements SimulationExecutorFactory {

	private static final Logger logger = LoggerFactory.getLogger(AsyncSimulationExecutorFactory.class);

	private final FutureExecutor futureExecutor;
	private final String outputLogsFileName;

	public AsyncSimulationExecutorFactory(FutureExecutor futureExecutor, String outputLogsFileName) {
		this.futureExecutor = futureExecutor;
		this.outputLogsFileName = outputLogsFileName;
	}

	@Override
	public SimulationExecutor create(ExecutionListener listener, SimulationExecutorParameters parameters) {

		logger.info("Creating simulation executor for parameters '{}'.", parameters);
		
		SimulationExecutor syncExecutor = new SimulationExecutorImpl(listener, parameters, outputLogsFileName);
		SimulationExecutor asyncExecutor = new AsyncSimulationExecutorDecorator(syncExecutor, futureExecutor);

		return asyncExecutor;
	}

}
