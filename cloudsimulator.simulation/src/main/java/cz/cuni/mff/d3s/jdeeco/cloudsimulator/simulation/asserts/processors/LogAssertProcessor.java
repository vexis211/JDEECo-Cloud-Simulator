package cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.asserts.processors;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class LogAssertProcessor extends BaseAssertProcessor {

	private static Logger logger = LogManager.getLogger(LogAssertProcessor.class);

	public LogAssertProcessor(String assertGroup) {
		super(assertGroup);
	}

	@Override
	public void processSuccess(String message) {
		logger.info(String.format("Assert succedeed - Group: '%s' - Message: '%s'", getAssertGroup(), message));
	}

	@Override
	public void processFail(String message) {
		logger.info(String.format("Assert failed!!! - Group: '%s' - Message: '%s'", getAssertGroup(), message));
	}
}
