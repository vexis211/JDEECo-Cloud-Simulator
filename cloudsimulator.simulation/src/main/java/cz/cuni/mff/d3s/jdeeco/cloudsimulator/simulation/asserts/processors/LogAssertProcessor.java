package cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.asserts.processors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogAssertProcessor extends BaseAssertProcessor {

	private static Logger logger = LoggerFactory.getLogger(LogAssertProcessor.class);

	public LogAssertProcessor(String assertGroup) {
		super(assertGroup);
	}

	@Override
	public void processSuccess(String message) {
		logger.info("Assert succedeed - Group: '{}' - Message: '{}'", getAssertGroup(), message);
	}

	@Override
	public void processFail(String message) {
		logger.info("Assert failed!!! - Group: '{}' - Message: '{}'", getAssertGroup(), message);
	}
}
