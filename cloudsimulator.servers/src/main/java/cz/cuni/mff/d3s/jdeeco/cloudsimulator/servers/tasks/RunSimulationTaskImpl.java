package cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.tasks;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.SimulationId;

public class RunSimulationTaskImpl extends WorkerTaskImpl implements RunSimulationTask {

	private static final long serialVersionUID = -956877781050491072L;

	private final SimulationId simulationId;

	public RunSimulationTaskImpl(SimulationId simulationId) {
		this.simulationId = simulationId;
	}

	@Override
	public SimulationId getSimulationId() {
		return simulationId;
	}
}
