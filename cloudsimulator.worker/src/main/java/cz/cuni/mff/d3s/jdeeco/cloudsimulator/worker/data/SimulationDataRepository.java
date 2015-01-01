package cz.cuni.mff.d3s.jdeeco.cloudsimulator.worker.data;

public interface SimulationDataRepository {
	String getData(String sourceUri);
	void saveResults(SimulationData data, String targetUri, String targetLogsUri);
	void clear();
}
