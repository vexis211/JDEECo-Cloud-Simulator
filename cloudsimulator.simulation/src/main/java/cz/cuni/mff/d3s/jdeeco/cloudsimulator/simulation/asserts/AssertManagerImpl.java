package cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.asserts;

import java.util.EnumSet;
import java.util.concurrent.ConcurrentHashMap;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.data.AssertAction;

public class AssertManagerImpl implements AssertManager {

	private final ConcurrentHashMap<String, AssertProcessor> groups2processors = new ConcurrentHashMap<String, AssertProcessor>();
	private final AssertConfiguration assertConfiguration;
	private final AssertProcessorFactory processorFactory;

	public AssertManagerImpl(AssertConfiguration assertConfiguration, AssertProcessorFactory assertProcessorFactory) {
		this.assertConfiguration = assertConfiguration;
		this.processorFactory = assertProcessorFactory;
	}

	@Override
	public AssertProcessor getProcessor(String assertGroup) {
		if (!groups2processors.containsKey(assertGroup)) {
			EnumSet<AssertAction> assertActions = assertConfiguration.getAssertActions(assertGroup);
			groups2processors.putIfAbsent(assertGroup, processorFactory.create(assertGroup, assertActions));
		}

		return groups2processors.get(assertGroup);
	}
}
