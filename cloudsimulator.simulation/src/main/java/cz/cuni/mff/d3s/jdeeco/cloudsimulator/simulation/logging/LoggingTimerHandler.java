package cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.timers.TimerHandler;

public class LoggingTimerHandler implements TimerHandler {

	private static Logger logger = LoggerFactory.getLogger(LoggingTimerHandler.class);

	@Override
	public void start(String id) {
		logger.info("Timer started - ID: '{}'.", id);
	}

	@Override
	public void stop(String id) {
		logger.info("Timer stopped - ID: '{}'.", id);
	}

	@Override
	public void reset(String id) {
		logger.info("Timer reset - ID: '{}'.", id);
	}
}
