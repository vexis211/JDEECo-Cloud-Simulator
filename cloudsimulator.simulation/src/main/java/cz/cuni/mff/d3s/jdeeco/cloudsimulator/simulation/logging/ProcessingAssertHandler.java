package cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.logging;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.asserts.AssertHandler;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.asserts.AssertManager;

public class ProcessingAssertHandler implements AssertHandler {

	private AssertManager assertManager;

	public ProcessingAssertHandler(AssertManager assertManager) {
		this.assertManager = assertManager;
	}

	@Override
	public void fail(String message, String assertionGroup) {
		assertManager.getProcessor(assertionGroup).processFail(message);
	}

	@Override
	public void success(String message, String assertionGroup) {
		assertManager.getProcessor(assertionGroup).processSuccess(message);
	}
}
