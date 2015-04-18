package cz.cuni.mff.d3s.jdeeco.cloudsimulator.worker.data;

public class SimulationDataImpl implements SimulationData {
	
	private final String executionPath;
	private final String resultsPath;
	private final String logsPath;

	public SimulationDataImpl(String executionPath, String resultsPath, String logsPath) {
		this.executionPath = executionPath;
		this.resultsPath = resultsPath;
		this.logsPath = logsPath;
	}
	
	@Override
	public String getExecutionPath() {
		return executionPath;
	}

	@Override
	public String getResultsPath() {
		return resultsPath;
	}

	@Override
	public String getLogsPath() {
		return logsPath;
	}
}
