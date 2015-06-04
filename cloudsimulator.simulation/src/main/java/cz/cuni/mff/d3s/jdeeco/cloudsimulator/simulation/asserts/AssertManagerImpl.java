package cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.asserts;

import java.util.EnumSet;
import java.util.HashMap;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.data.AssertAction;

public class AssertManagerImpl implements AssertManager {

	private final HashMap<String, AssertProcessor> groups2processors = new HashMap<String, AssertProcessor>();
	private final AssertConfiguration assertConfiguration;
	private final AssertProcessorFactory processorFactory;

	public AssertManagerImpl(AssertConfiguration assertConfiguration, AssertProcessorFactory assertProcessorFactory) {
		this.assertConfiguration = assertConfiguration;
		this.processorFactory = assertProcessorFactory;
	}

	@Override
	public AssertProcessor getProcessor(String assertGroup) {
		AssertProcessor processor;

		if (groups2processors.containsKey(assertGroup)) {
			processor = groups2processors.get(assertGroup);
		} else {
			EnumSet<AssertAction> assertActions = assertConfiguration.getAssertActions(assertGroup);
			processor = processorFactory.create(assertGroup, assertActions);
			groups2processors.put(assertGroup, processor);
		}

		return processor;
	}
}
