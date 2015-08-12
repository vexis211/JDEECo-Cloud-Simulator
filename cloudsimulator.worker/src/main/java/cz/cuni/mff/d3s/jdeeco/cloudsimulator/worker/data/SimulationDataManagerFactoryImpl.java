package cz.cuni.mff.d3s.jdeeco.cloudsimulator.worker.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.FutureExecutor;

public class SimulationDataManagerFactoryImpl implements SimulationDataManagerFactory {

	private static final Logger logger = LoggerFactory.getLogger(SimulationDataManagerFactoryImpl.class);

	private final FutureExecutor futureExecutor;
	private final SimulationDataRepository simulationDataRepository;
	private final String localExecutionsRoot;
	private final String resultsDirectoryName;
	private final String logsDirectoryName;

	public SimulationDataManagerFactoryImpl(FutureExecutor futureExecutor,
			SimulationDataRepository simulationDataRepository, String localExecutionsRoot, String resultsDirectoryName,
			String logsDirectoryName) {
		this.futureExecutor = futureExecutor;
		this.simulationDataRepository = simulationDataRepository;
		this.localExecutionsRoot = localExecutionsRoot;
		this.resultsDirectoryName = resultsDirectoryName;
		this.logsDirectoryName = logsDirectoryName;
	}

	@Override
	public SimulationDataManager create(SimulationDataListener listener) {
		logger.info("Creating simulation data manager.");
		
		SimulationDataManager manager = new SimulationDataManagerImpl(localExecutionsRoot, resultsDirectoryName,
				logsDirectoryName, futureExecutor, simulationDataRepository, listener);
		return manager;
	}
}
