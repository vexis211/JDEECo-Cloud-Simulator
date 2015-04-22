package cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers;

public interface SimulationDataStorageService {

	String getPackagePath(int executionId);

	void saveResults(String localSourcePath, SimulationId simulationId);
	void saveLogs(String localSourcePath, SimulationId simulationId);
}
