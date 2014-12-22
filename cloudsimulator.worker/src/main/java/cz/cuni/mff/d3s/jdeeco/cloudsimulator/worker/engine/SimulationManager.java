package cz.cuni.mff.d3s.jdeeco.cloudsimulator.worker.engine;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.tasks.RunSimulationTask;

public interface SimulationManager {

	void runSimulation(RunSimulationTask task);
	void stopAndCleanSimulations();
	

}
