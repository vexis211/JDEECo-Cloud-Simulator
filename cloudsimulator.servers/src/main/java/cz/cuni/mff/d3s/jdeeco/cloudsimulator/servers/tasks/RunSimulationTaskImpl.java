package cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.tasks;

public class RunSimulationTaskImpl extends WorkerTaskImpl implements RunSimulationTask {

	private static final long serialVersionUID = -956877781050491072L;

	private final int simulationRunId;
	private final String dataUri;
	private final String logFileUri;
	private final String resultsUri;

	public RunSimulationTaskImpl(int simulationRunId, String dataUri, String logFileUri, String resultsUri) {
		this.simulationRunId = simulationRunId;
		this.dataUri = dataUri;
		this.logFileUri = logFileUri;
		this.resultsUri = resultsUri;
	}

	@Override
	public int getSimulationRunId() {
		return simulationRunId;
	}

	@Override
	public String getDataUri() {
		return dataUri;
	}

	@Override
	public String getLogFileUri() {
		return logFileUri;
	}

	@Override
	public String getResultsUri() {
		return resultsUri;
	}
}
