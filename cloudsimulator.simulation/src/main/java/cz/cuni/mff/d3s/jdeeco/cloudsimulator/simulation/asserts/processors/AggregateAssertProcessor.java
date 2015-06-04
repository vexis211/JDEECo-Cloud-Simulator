package cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.asserts.processors;

import java.util.List;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.asserts.AssertProcessor;

public class AggregateAssertProcessor extends BaseAssertProcessor {

	private final List<AssertProcessor> processors;

	public AggregateAssertProcessor(String assertGroup, List<AssertProcessor> processors) {
		super(assertGroup);
		this.processors = processors;
	}

	@Override
	public void processSuccess(String message) {
		for (AssertProcessor processor : processors) {
			processor.processSuccess(message);
		}
	}

	@Override
	public void processFail(String message) {
		for (AssertProcessor processor : processors) {
			processor.processFail(message);
		}
	}
}
