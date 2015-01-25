package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.data;

public interface SimulationRunWorkerMapping {
	String getWorkerId(int simulationRunId);
	Integer getSimulationRunId(String workerId);
	
	void put(String workerId, int simulationRunId);
}
