package cz.cuni.mff.d3s.jdeeco.cloudsimulator.worker.data;

public interface SimulationDataRepository {
	String getPackagePath(int executionId);
	void saveResults(SimulationData data, int runId);
	void clear();
}
