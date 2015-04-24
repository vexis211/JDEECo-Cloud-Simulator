package cz.cuni.mff.d3s.jdeeco.cloudsimulator.worker.execution;

import java.io.File;

import org.apache.log4j.Logger;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.extensions.PathEx;

public class SimulationExecutorImpl implements SimulationExecutor {

	private final Logger logger = Logger.getLogger(SimulationExecutorImpl.class);

	private final ExecutionListener listener;
	private final SimulationExecutorParameters parameters;
	private final String outputLogsFileName;

	private Process process;
	private boolean stoppedManually;

	public SimulationExecutorImpl(ExecutionListener listener, SimulationExecutorParameters parameters,
			String outputLogsFileName) {
		this.listener = listener;
		this.parameters = parameters;
		this.outputLogsFileName = outputLogsFileName;
	}

	@Override
	public SimulationExecutorParameters getParameters() {
		return parameters;
	}

	@Override
	public void start() {
		this.stoppedManually = false;
		
		String executionFilePath = PathEx.combine(parameters.getRunExecutionDirectory(), "TODO"); // TODO
		ProcessBuilder processBuilder = new ProcessBuilder(String.format("java -jar \"%s\"", executionFilePath));

		processBuilder.directory(new File(parameters.getRunExecutionDirectory()));

		processBuilder.redirectErrorStream(true);
		File logFile = new File(PathEx.combine(parameters.getRunLogsPath(), outputLogsFileName));
		processBuilder.redirectOutput(logFile);
		
		logger.info("Starting simulation run with parameters: " + parameters);
		logger.info("Logging from simulation run is redirected to: " + logFile);
		try {
			this.process = processBuilder.start();
			this.process.waitFor();

			logger.info("Simulation run ended. Parameters: " + parameters);

			if (stoppedManually) {
				listener.executionStopped(this);
			}
			else {
				listener.executionEnded(this);
			}
		} catch (Exception e) {
			logger.error("Error occured while running simulation with parameters: " + parameters, e);
			listener.exceptionOccured(this, e);
		}
	}

	@Override
	public void stop() {
		logger.info("Stopping simulation run with parameters: " + parameters);
		stoppedManually = true;
		this.process.destroy(); // by killing process waitFor will end
	}
}
