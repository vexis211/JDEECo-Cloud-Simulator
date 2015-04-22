package cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.tasks;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.SimulationId;

public class StopSimulationTaskImpl extends WorkerTaskImpl implements StopSimulationTask {

	private static final long serialVersionUID = -5967562853897509291L;

	private final SimulationId simulationId;

	public StopSimulationTaskImpl(SimulationId simulationId) {
		this.simulationId = simulationId;
	}

	@Override
	public SimulationId getSimulationId() {
		return simulationId;
	}
}
