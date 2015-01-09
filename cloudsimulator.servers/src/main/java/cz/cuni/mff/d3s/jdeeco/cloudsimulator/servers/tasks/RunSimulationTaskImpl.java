package cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.tasks;

public class RunSimulationTaskImpl extends WorkerTaskImpl implements RunSimulationTask {

	private static final long serialVersionUID = -956877781050491072L;

	private final int simulationRunId;
	private final String dataSource;
	private final String logsTarget;
	private final String resultsTarget;

	public RunSimulationTaskImpl(int simulationRunId, String dataSource, String logsTarget, String resultsTarget) {
		this.simulationRunId = simulationRunId;
		this.dataSource = dataSource;
		this.logsTarget = logsTarget;
		this.resultsTarget = resultsTarget;
	}

	@Override
	public int getSimulationRunId() {
		return simulationRunId;
	}

	@Override
	public String getDataSource() {
		return dataSource;
	}

	@Override
	public String getLogsTarget() {
		return logsTarget;
	}

	@Override
	public String getResultsTarget() {
		return resultsTarget;
	}
}
