package cz.cuni.mff.d3s.jdeeco.cloudsimulator.worker.data;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.FutureExecutor;

public class SimulationDataManagerFactoryImpl implements SimulationDataManagerFactory {

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
		SimulationDataManager manager = new SimulationDataManagerImpl(localExecutionsRoot, resultsDirectoryName,
				logsDirectoryName, futureExecutor, simulationDataRepository, listener);
		return manager;
	}
}
