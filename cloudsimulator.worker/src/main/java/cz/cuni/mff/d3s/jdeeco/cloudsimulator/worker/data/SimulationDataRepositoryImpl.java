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
	public String getPackagePath(String dataName) {
		if (simulationDataCache.containsKey(dataName)) {
			return simulationDataCache.get(dataName);
		}

		String packagePath = simulationDataStorageService.getPackagePath(dataName);

		simulationDataCache.put(dataName, packagePath);
		return packagePath;
	}

	@Override
	public void saveResults(SimulationData data, String dataName) {
		// results
		simulationDataStorageService.saveResults(data.getResultsPath(), dataName);
		// logs
		simulationDataStorageService.saveLogs(data.getLogPath(), dataName);
	}

	@Override
	public void clear() {
		simulationDataCache.clear();
	}
}
