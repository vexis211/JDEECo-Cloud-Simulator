package cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.logging;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.asserts.AssertHandler;

public class LoggingAssertHandler implements AssertHandler {

	private static Logger logger = LogManager.getLogger(LoggingAssertHandler.class);

	@Override
	public void fail(String message, String assertionGroup) {
		logger.info(String.format("Assert failed!!! - Group: '%s' - Message: '%s'", assertionGroup, message));
	}

	@Override
	public void success(String message, String assertionGroup) {
		logger.info(String.format("Assert succedeed - Group: '%s' - Message: '%s'", assertionGroup, message));
	}
}
