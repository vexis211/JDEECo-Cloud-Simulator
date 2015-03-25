package cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers;

public interface SimulationDataStorageService {

	String getPackagePath(String dataName);

	void saveResults(String sourcePath, String target);
	void saveLogs(String sourcePath, String target);
}
