package cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.tasks;

public class RunSimulationTaskImpl extends WorkerTaskImpl implements RunSimulationTask {

	private static final long serialVersionUID = -956877781050491072L;

	private final int simulationRunId;
	private final String packageName;

	public RunSimulationTaskImpl(int simulationRunId, String dataName) {
		this.simulationRunId = simulationRunId;
		this.packageName = dataName;
	}

	@Override
	public int getSimulationRunId() {
		return simulationRunId;
	}

	@Override
	public String getPackageName() {
		return packageName;
	}
}
