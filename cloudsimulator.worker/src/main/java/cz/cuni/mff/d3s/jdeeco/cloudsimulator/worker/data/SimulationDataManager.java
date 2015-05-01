package cz.cuni.mff.d3s.jdeeco.cloudsimulator.worker.data;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.Disposable;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.SimulationId;


public interface SimulationDataManager extends Disposable {

	void prepareData(SimulationId simulationId, String startupFile);
	void saveResults(SimulationId simulationId, SimulationData data);
}
