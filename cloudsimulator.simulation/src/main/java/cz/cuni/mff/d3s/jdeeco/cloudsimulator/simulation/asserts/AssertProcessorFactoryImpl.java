package cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.asserts;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.asserts.processors.AggregateAssertProcessor;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.asserts.processors.LogAssertProcessor;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.data.AssertAction;

public class AssertProcessorFactoryImpl implements AssertProcessorFactory {

	@SuppressWarnings("unused")
	private static Logger logger = LogManager.getLogger(AssertProcessorFactoryImpl.class);

	@Override
	public AssertProcessor create(String assertGroup, EnumSet<AssertAction> assertActions) {

		List<AssertProcessor> processors = new ArrayList<AssertProcessor>();

		for (AssertAction action : assertActions) {
			switch (action) {
			case Log:
				processors.add(new LogAssertProcessor(assertGroup));
				break;
			case ExitRun:
				// TODO
				break;
			case ExitExecution:
				// TODO
				break;

			default:
				throw new EnumConstantNotPresentException(action.getClass(), action.toString());
			}
		}

		return processors.size() == 1 ? processors.get(0) : new AggregateAssertProcessor(assertGroup, processors);
	}

}
