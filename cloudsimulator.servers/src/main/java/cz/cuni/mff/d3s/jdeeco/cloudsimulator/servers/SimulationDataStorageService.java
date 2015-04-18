package cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers;

public interface SimulationDataStorageService {

	String getPackagePath(int executionId);

	void saveResults(String sourcePath, int runId);
	void saveLogs(String sourcePath, int runId);
}
