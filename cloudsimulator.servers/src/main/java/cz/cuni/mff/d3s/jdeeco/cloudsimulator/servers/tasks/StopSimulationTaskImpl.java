package cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.tasks;

public class StopSimulationTaskImpl extends WorkerTaskImpl implements StopSimulationTask {

	private static final long serialVersionUID = -5967562853897509291L;

	private final int simulationRunId;

	public StopSimulationTaskImpl(int simulationRunId) {
		this.simulationRunId = simulationRunId;
	}

	@Override
	public int getSimulationRunId() {
		return simulationRunId;
	}
}
