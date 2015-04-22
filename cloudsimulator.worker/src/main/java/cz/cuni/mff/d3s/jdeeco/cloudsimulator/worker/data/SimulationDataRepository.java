package cz.cuni.mff.d3s.jdeeco.cloudsimulator.worker.data;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.SimulationId;

public interface SimulationDataRepository {
	String getPackagePath(SimulationId simulationId);
	void saveResults(SimulationData data, SimulationId simulationId);
	void clear();
}
