package cz.cuni.mff.d3s.jdeeco.cloudsimulator.worker.data;

public interface SimulationDataRepository {
	String getData(String source);
	void saveResults(SimulationData data, String resultsTarget, String logsTarget);
	void clear();
}
