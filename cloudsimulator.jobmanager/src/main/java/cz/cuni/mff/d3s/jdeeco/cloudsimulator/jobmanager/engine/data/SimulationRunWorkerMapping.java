package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.data;

public interface SimulationRunWorkerMapping {
	String getWorkerId(int simulationRunId);
	int getSimulationRunId(String workerId);
}
