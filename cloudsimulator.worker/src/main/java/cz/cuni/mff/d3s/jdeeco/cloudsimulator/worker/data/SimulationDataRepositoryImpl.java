package cz.cuni.mff.d3s.jdeeco.cloudsimulator.worker.data;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.SimulationDataStorageService;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.SimulationId;

public class SimulationDataRepositoryImpl implements SimulationDataRepository {

	private static final Logger logger = LoggerFactory.getLogger(SimulationDataRepositoryImpl.class);

	private final HashMap<SimulationId, String> simulationDataCache = new HashMap<>();
	private final SimulationDataStorageService simulationDataStorageService;

	public SimulationDataRepositoryImpl(SimulationDataStorageService simulationDataStorageService) {
		this.simulationDataStorageService = simulationDataStorageService;
	}

	@Override
	public String getPackagePath(SimulationId simulationId) {
		logger.info("Getting package path for simulation ID '{}'.", simulationId);

		if (simulationDataCache.containsKey(simulationId)) {
			String packagePath = simulationDataCache.get(simulationId);
			logger.info("Package path for simulation ID '{}' is '{}' (already cached).", simulationId, packagePath);
			return packagePath;
		}

		String packagePath = simulationDataStorageService.getPackagePath(simulationId.getExecutionId());

		simulationDataCache.put(simulationId, packagePath);
		logger.info("Package path for simulation ID '{}' is '{}' (added to cache).", simulationId, packagePath);

		return packagePath;
	}

	@Override
	public void saveResults(SimulationData data, SimulationId simulationId) {
		logger.info("Saving results. Simulation ID: '{}', data: '{}'.", simulationId, data);

		// results
		simulationDataStorageService.saveResults(data.getLocalResultsPath(), simulationId);
		// logs
		simulationDataStorageService.saveLogs(data.getLocalLogsPath(), simulationId);

		logger.info("Results saved. Simulation ID: '{}', data: '{}'.", simulationId, data);
	}

	@Override
	public void clear() {
		logger.info("Clearing package path cache.");

		simulationDataCache.clear();
	}
}
