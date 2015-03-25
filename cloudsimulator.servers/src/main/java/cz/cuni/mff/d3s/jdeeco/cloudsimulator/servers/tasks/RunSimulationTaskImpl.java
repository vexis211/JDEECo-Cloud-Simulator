package cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.tasks;

public class RunSimulationTaskImpl extends WorkerTaskImpl implements RunSimulationTask {

	private static final long serialVersionUID = -956877781050491072L;

	private final int simulationRunId;

	public RunSimulationTaskImpl(int simulationRunId) {
		this.simulationRunId = simulationRunId;
	}

	@Override
	public int getSimulationRunId() {
		return simulationRunId;
	}
}
