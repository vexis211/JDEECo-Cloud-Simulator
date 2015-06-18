package cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.asserts;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.data.SimulationExitReason;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.asserts.processors.AggregateAssertProcessor;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.asserts.processors.FailExitAssertProcessor;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.asserts.processors.LogAssertProcessor;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.data.AssertAction;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.startup.SimulationController;

public class AssertProcessorFactoryImpl implements AssertProcessorFactory {

	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(AssertProcessorFactoryImpl.class);

	private final SimulationController simulationController;

	public AssertProcessorFactoryImpl(SimulationController simulationController) {
		this.simulationController = simulationController;
	}

	@Override
	public AssertProcessor create(String assertGroup, EnumSet<AssertAction> assertActions) {

		List<AssertProcessor> processors = new ArrayList<AssertProcessor>();

		for (AssertAction action : assertActions) {
			switch (action) {
			case Log:
				processors.add(new LogAssertProcessor(assertGroup));
				break;
			case ExitRun:
				processors.add(new FailExitAssertProcessor(assertGroup, SimulationExitReason.RunExitCalled,
						simulationController));
				break;
			case ExitExecution:
				processors.add(new FailExitAssertProcessor(assertGroup, SimulationExitReason.ExecutionExitCalled,
						simulationController));
				break;
			default:
				throw new EnumConstantNotPresentException(action.getClass(), action.toString());
			}
		}

		return processors.size() == 1 ? processors.get(0) : new AggregateAssertProcessor(assertGroup, processors);
	}
}
