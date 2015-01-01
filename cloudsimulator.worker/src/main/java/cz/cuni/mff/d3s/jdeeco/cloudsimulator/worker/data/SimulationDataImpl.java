package cz.cuni.mff.d3s.jdeeco.cloudsimulator.worker.data;

public class SimulationDataImpl implements SimulationData {
	
	private final String executionPath;
	private final String logPath;

	public SimulationDataImpl(String executionPath, String logPath) {
		this.executionPath = executionPath;
		this.logPath = logPath;
	}
	
	@Override
	public String getExecutionPath() {
		return executionPath;
	}

	@Override
	public String getLogPath() {
		return logPath;
	}

}
