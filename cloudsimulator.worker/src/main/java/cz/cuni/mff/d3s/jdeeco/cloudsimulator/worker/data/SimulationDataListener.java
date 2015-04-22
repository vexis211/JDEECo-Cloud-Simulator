package cz.cuni.mff.d3s.jdeeco.cloudsimulator.worker.data;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.SimulationId;

public interface SimulationDataListener {

	void dataPrepared(SimulationId simulationId, SimulationData data);
	void resultsSaved(SimulationId simulationId);
}
