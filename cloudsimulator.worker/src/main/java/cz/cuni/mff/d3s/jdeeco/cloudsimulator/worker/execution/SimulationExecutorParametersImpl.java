package cz.cuni.mff.d3s.jdeeco.cloudsimulator.worker.execution;

public class SimulationExecutorParametersImpl implements SimulationExecutorParameters {

	private String executionPath;
	private String logPath;

	public SimulationExecutorParametersImpl(String executionPath, String logPath) {
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
