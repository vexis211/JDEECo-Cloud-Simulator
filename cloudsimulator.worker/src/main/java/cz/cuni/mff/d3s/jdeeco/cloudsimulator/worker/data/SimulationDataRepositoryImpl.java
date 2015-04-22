package cz.cuni.mff.d3s.jdeeco.cloudsimulator.worker.data;

import java.util.HashMap;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.SimulationDataStorageService;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.SimulationId;

public class SimulationDataRepositoryImpl implements SimulationDataRepository {

	private final HashMap<SimulationId, String> simulationDataCache = new HashMap<>();
	private final SimulationDataStorageService simulationDataStorageService;

	public SimulationDataRepositoryImpl(SimulationDataStorageService simulationDataStorageService) {
		this.simulationDataStorageService = simulationDataStorageService;
	}

	@Override
	public String getPackagePath(SimulationId simulationId) {
		if (simulationDataCache.containsKey(simulationId)) {
			return simulationDataCache.get(simulationId);
		}

		String packagePath = simulationDataStorageService.getPackagePath(simulationId.getExecutionId());

		simulationDataCache.put(simulationId, packagePath);
		return packagePath;
	}

	@Override
	public void saveResults(SimulationData data, SimulationId simulationId) {
		// results
		simulationDataStorageService.saveResults(data.getLocalResultsPath(), simulationId);
		// logs
		simulationDataStorageService.saveLogs(data.getLocalLogsPath(), simulationId);
	}

	@Override
	public void clear() {
		simulationDataCache.clear();
	}
}
