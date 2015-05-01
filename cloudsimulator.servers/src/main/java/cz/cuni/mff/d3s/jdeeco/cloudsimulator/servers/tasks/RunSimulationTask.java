package cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.tasks;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.SimulationId;

public interface RunSimulationTask extends WorkerTask {
	SimulationId getSimulationId();
	String getStartupFile();
}
