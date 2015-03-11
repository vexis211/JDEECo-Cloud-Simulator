package cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers;

public interface SimulationDataStorageService {

	String getDataPath(String dataName);

	void saveResults(String source, String target);
	void saveLogs(String source, String target);
}
