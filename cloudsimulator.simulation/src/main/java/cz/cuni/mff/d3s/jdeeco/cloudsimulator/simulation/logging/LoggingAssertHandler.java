package cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.asserts.AssertHandler;

public class LoggingAssertHandler implements AssertHandler {

	private static Logger logger = LoggerFactory.getLogger(LoggingAssertHandler.class);

	@Override
	public void fail(String message, String assertionGroup) {
		logger.info("Assert failed!!! - Group: '{}' - Message: '{}'", assertionGroup, message);
	}

	@Override
	public void success(String message, String assertionGroup) {
		logger.info("Assert succedeed - Group: '{}' - Message: '{}'", assertionGroup, message);
	}
}
