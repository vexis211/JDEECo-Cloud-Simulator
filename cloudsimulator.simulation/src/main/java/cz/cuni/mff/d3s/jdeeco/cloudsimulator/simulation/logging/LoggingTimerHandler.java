package cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.logging;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.timers.TimerHandler;

public class LoggingTimerHandler implements TimerHandler {

	private static Logger logger = LogManager.getLogger(LoggingTimerHandler.class);

	@Override
	public void start(String id) {
		logger.info(String.format("Timer started - ID: '%s'.", id));
	}

	@Override
	public void stop(String id) {
		logger.info(String.format("Timer stopped - ID: '%s'.", id));
	}

	@Override
	public void reset(String id) {
		logger.info(String.format("Timer reset - ID: '%s'.", id));
	}
}
