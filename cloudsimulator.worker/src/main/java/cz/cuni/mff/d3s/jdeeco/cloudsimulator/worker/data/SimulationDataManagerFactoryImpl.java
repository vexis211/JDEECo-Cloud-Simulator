package cz.cuni.mff.d3s.jdeeco.cloudsimulator.worker.data;

import javax.annotation.Resource;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.worker.FutureExecutor;

public class SimulationDataManagerFactoryImpl implements SimulationDataManagerFactory {

	@Resource
	private FutureExecutor futureExecutor;

	@Resource
	private SimulationDataRepository dataRepository;

	@Override
	public SimulationDataManager create(String dataParentDirectory, String logParentDirectory,
			SimulationDataListener listener) {
		SimulationDataManager manager = new SimulationDataManagerImpl(dataParentDirectory, logParentDirectory,
				futureExecutor, dataRepository, listener);
		return manager;
	}
}
