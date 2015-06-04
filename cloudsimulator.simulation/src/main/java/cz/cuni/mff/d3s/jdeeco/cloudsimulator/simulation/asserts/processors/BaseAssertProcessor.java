package cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.asserts.processors;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.asserts.AssertProcessor;

public abstract class BaseAssertProcessor implements AssertProcessor {

	private final String assertGroup;
	
	public BaseAssertProcessor(String assertGroup) {
		this.assertGroup = assertGroup;
	}

	@Override
	public String getAssertGroup() {
		return assertGroup;
	}
}
