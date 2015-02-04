package cz.cuni.mff.d3s.jdeeco.cloudsimulator.worker.engine;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.tasks.RunSimulationTask;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.tasks.StopSimulationTask;

public interface SimulationManager {
	void runSimulation(RunSimulationTask task);
	void stopSimulation(StopSimulationTask task);
}
