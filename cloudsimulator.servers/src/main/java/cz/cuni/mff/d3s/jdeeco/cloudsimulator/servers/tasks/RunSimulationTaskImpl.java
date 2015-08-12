package cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.tasks;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.SimulationId;

public class RunSimulationTaskImpl extends WorkerTaskImpl implements RunSimulationTask {

	private static final long serialVersionUID = -956877781050491072L;

	private final SimulationId simulationId;
	private final String startupFile;

	public RunSimulationTaskImpl(SimulationId simulationId, String startupFile) {
		this.simulationId = simulationId;
		this.startupFile = startupFile;
	}

	@Override
	public SimulationId getSimulationId() {
		return simulationId;
	}

	@Override
	public String getStartupFile() {
		return startupFile;
	}

	@Override
	public String toString() {
		return "RunSimulationTaskImpl [simulationId=" + simulationId + ", startupFile=" + startupFile + "]";
	}
}
