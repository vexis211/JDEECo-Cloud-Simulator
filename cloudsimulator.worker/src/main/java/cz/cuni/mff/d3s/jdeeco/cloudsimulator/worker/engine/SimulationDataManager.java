package cz.cuni.mff.d3s.jdeeco.cloudsimulator.worker.engine;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.tasks.RunSimulationTask;

public interface SimulationDataManager {

	void getData(RunSimulationTask task, );
	
	String uploadResults(String dataId);
}
