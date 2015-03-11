package cz.cuni.mff.d3s.jdeeco.cloudsimulator.worker.data;

import java.util.HashMap;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.SimulationDataStorageService;

public class SimulationDataRepositoryImpl implements SimulationDataRepository {

	private final HashMap<String, String> simulationDataCache = new HashMap<>();

	private final SimulationDataStorageService simulationDataStorageService;

	public SimulationDataRepositoryImpl(SimulationDataStorageService simulationDataStorageService) {
		this.simulationDataStorageService = simulationDataStorageService;
	}

	@Override
	public String getData(String dataName) {
		if (simulationDataCache.containsKey(dataName)) {
			return simulationDataCache.get(dataName);
		}

		String dataPath = simulationDataStorageService.getDataPath(dataName);

		simulationDataCache.put(dataName, dataPath);
		return dataPath;
	}

	@Override
	public void saveResults(SimulationData data, String target) {
		// results
		simulationDataStorageService.saveResults(data.getExecutionPath(), target);
		// logs
		simulationDataStorageService.saveLogs(data.getLogPath(), target);
	}

	@Override
	public void clear() {
		simulationDataCache.clear();
	}
}
