package cz.cuni.mff.d3s.jdeeco.cloudsimulator.worker.execution;

import java.io.File;

public class SimulationExecutorImpl implements SimulationExecutor {
	
	private final ExecutionListener listener;
	private final SimulationExecutorParameters parameters;
	
	private Process process;
	private boolean stoppedManually;

	public SimulationExecutorImpl(ExecutionListener listener, SimulationExecutorParameters parameters) {
		this.listener = listener;
		this.parameters = parameters;
	}

	@Override
	public void start() {
		ProcessBuilder processBuilder = new ProcessBuilder(parameters.getExecutionPath());
		
		processBuilder.directory(new File(parameters.getExecutionPath()).getParentFile());
		
		processBuilder.redirectErrorStream(true);
		processBuilder.redirectOutput(new File(parameters.getLogPath()));
		
		try {
			this.process = processBuilder.start();
			this.process.waitFor();
			
			if (stoppedManually) listener.executionStopped(this);
			else listener.executionEnded(this);
			
		} catch (Exception e) {
			e.printStackTrace();
			listener.exceptionOccured(this);
		}
	}

	@Override
	public void stop() {
		stoppedManually = true;
		this.process.destroy();
	}
}
