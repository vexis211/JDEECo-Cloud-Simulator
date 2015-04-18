package cz.cuni.mff.d3s.jdeeco.cloudsimulator.worker.data;

import java.util.HashMap;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.SimulationDataStorageService;

public class SimulationDataRepositoryImpl implements SimulationDataRepository {

	private final HashMap<Integer, String> simulationDataCache = new HashMap<>();
	private final SimulationDataStorageService simulationDataStorageService;

	public SimulationDataRepositoryImpl(SimulationDataStorageService simulationDataStorageService) {
		this.simulationDataStorageService = simulationDataStorageService;
	}

	@Override
	public String getPackagePath(int executionId) {
		if (simulationDataCache.containsKey(executionId)) {
			return simulationDataCache.get(executionId);
		}

		String packagePath = simulationDataStorageService.getPackagePath(executionId);

		simulationDataCache.put(executionId, packagePath);
		return packagePath;
	}

	@Override
	public void saveResults(SimulationData data, int runId) {
		// results
		simulationDataStorageService.saveResults(data.getResultsPath(), runId);
		// logs
		simulationDataStorageService.saveLogs(data.getLogsPath(), runId);
	}

	@Override
	public void clear() {
		simulationDataCache.clear();
	}
}
