package cz.cuni.mff.d3s.jdeeco.cloudsimulator.worker.data;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.FutureExecutor;

public class SimulationDataManagerFactoryImpl implements SimulationDataManagerFactory {

	private final FutureExecutor futureExecutor;
	private final SimulationDataRepository simulationDataRepository;

	public SimulationDataManagerFactoryImpl(FutureExecutor futureExecutor, SimulationDataRepository simulationDataRepository) {
		this.futureExecutor = futureExecutor;
		this.simulationDataRepository = simulationDataRepository;		
	}

	@Override
	public SimulationDataManager create(String executionsRootDirectory, String resultsRootDirectory, String logsRootDirectory,
			SimulationDataListener listener) {
		SimulationDataManager manager = new SimulationDataManagerImpl(executionsRootDirectory, resultsRootDirectory, logsRootDirectory,
				futureExecutor, simulationDataRepository, listener);
		return manager;
	}
}
