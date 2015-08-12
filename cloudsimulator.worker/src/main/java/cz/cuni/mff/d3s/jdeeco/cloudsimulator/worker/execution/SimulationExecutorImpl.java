package cz.cuni.mff.d3s.jdeeco.cloudsimulator.worker.execution;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.data.SimulationExitReason;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.extensions.PathEx;

public class SimulationExecutorImpl implements SimulationExecutor {

	private static final Logger logger = LoggerFactory.getLogger(SimulationExecutorImpl.class);

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

		logger.info("Starting simulation run with parameters: '{}'.", parameters);

		String startupFilePath = PathEx.combine(parameters.getRunExecutionDirectory(), parameters.getStartupFile());
		String command = String.format("java -jar \"%s\"", startupFilePath);
		ProcessBuilder processBuilder = new ProcessBuilder(command);

		processBuilder.directory(new File(parameters.getRunExecutionDirectory()));

		processBuilder.redirectErrorStream(true);
		File logFile = new File(PathEx.combine(parameters.getRunLogsPath(), outputLogsFileName));
		processBuilder.redirectOutput(logFile);

		logger.info("Logging from simulation run is redirected to: {}", logFile);
		logger.info("Starting new process: '{}'.", command);

		try {
			this.process = processBuilder.start();
			
			logger.info("Waiting for process to end: '{}'.", command);
			int exitValue = this.process.waitFor();

			logger.info("Simulation run ended. Parameters: {}, Exit value: {}", parameters, exitValue);

			SimulationExitReason exitReason = SimulationExitReason.fromInt(exitValue);

			if (stoppedManually)
				listener.executionManuallyStopped(this);

			switch (exitReason) {
			case RunExitCalled:
			case ExecutionExitCalled:
			case ExceptionOccured:
				listener.executionStopped(this, exitReason);
				break;

			case Finished:
				listener.executionEnded(this);
				break;

			default:
				throw new EnumConstantNotPresentException(SimulationExitReason.class, String.valueOf(exitValue));
			}
		} catch (Exception e) {
			logger.error("Error occured while running simulation with parameters: " + parameters, e);
			listener.exceptionOccured(this, e);
		}
	}

	@Override
	public void stop() {
		logger.info("Stopping simulation run with parameters: {}", parameters);
		stoppedManually = true;
		this.process.destroy(); // by killing process waitFor will end
	}
}
