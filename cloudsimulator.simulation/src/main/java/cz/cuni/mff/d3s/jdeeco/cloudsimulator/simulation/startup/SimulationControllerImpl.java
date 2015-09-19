package cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.startup;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.cuni.mff.d3s.deeco.runners.DEECoSimulation;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.data.SimulationExitReason;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.settings.SimulationEndSettings;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.data.SimulationEndSpecificationType;

public class SimulationControllerImpl implements SimulationController {

	private static final long MAX_SIMUL_TIME_FOR_REAL_TIME = Long.MAX_VALUE;
	private static final long MAX_REAL_MILLIS_FOR_SIM_TIME = Long.MAX_VALUE;

	private static Logger logger = LoggerFactory.getLogger(SimulationControllerImpl.class);

	private boolean started = false;
	private java.util.concurrent.Future<?> simulationFuture;
	private SimulationExitReason exitReason = SimulationExitReason.Finished;
	
	@Override
	public SimulationExitReason start(DEECoSimulation simulation, SimulationEndSettings endSettings) throws Exception {

		if (this.started) throw new RuntimeException("Cannot use SimulationControllerImpl.start multiple times.");
		this.started = true;
		
		logger.info("Starting simulation...");

		java.util.concurrent.ExecutorService executor = java.util.concurrent.Executors.newSingleThreadExecutor();

		try {
			long simulationTime;
			long maxRealtimeInMillis;

			if (endSettings.getType() == SimulationEndSpecificationType.SimulationTime) {
				// stop on simulation time is solved inside simulation environment
				simulationTime = endSettings.getTime();
				maxRealtimeInMillis = MAX_REAL_MILLIS_FOR_SIM_TIME;
			} else {
				simulationTime = MAX_SIMUL_TIME_FOR_REAL_TIME;
				maxRealtimeInMillis = endSettings.getTime();
			}

			Runnable runnable = () -> simulation.start(simulationTime);
			this.simulationFuture = executor.submit(runnable);
			
			// this will wait but only for specified time
			this.simulationFuture.get(maxRealtimeInMillis, java.util.concurrent.TimeUnit.MILLISECONDS);
		} catch (final java.util.concurrent.TimeoutException e) {
			// Took too long!
			logger.info("Simulation stopped - real time limit exceeded.");
		} catch (final InterruptedException e) {
			// The thread was interrupted during sleep, wait or join
			if (exitReason == SimulationExitReason.Finished) {
				logger.error("Error occurred while simulating.", e);
				throw e;
			} else {
				logger.info("Simulation exited: InterruptedException");
			}
		} catch (final java.util.concurrent.ExecutionException e) {
			// An exception from within the Runnable task
			if (exitReason == SimulationExitReason.Finished) {
				logger.error("Error occurred while simulating.", e);
				throw e;
			} else {
				logger.info("Simulation exited: InterruptedException");
			}
		} finally {
			executor.shutdown();
		}
		
		logger.info("Ending simulation...");
		
		return this.exitReason;
	}

	@Override
	public void exitRun() {
		logger.info("Exit simulation run called...");
		this.exitReason = SimulationExitReason.RunExitCalled;
		this.simulationFuture.cancel(true);
	}

	@Override
	public void exitExecution() {
		logger.info("Exit simulation execution called...");
		this.exitReason = SimulationExitReason.ExecutionExitCalled;
		this.simulationFuture.cancel(true);
	}
}
