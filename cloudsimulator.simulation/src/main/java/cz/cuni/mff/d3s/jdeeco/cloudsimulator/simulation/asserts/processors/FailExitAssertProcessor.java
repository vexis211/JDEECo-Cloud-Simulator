package cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.asserts.processors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.data.SimulationExitReason;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.startup.SimulationController;

public class FailExitAssertProcessor extends BaseAssertProcessor {

	private static Logger logger = LoggerFactory.getLogger(FailExitAssertProcessor.class);
	
	private final SimulationController simulationController;
	private final SimulationExitReason exitReason;

	public FailExitAssertProcessor(String assertGroup, SimulationExitReason exitReason, SimulationController simulationController) {
		super(assertGroup);
		
		this.exitReason = exitReason;		
		this.simulationController = simulationController;
	}

	@Override
	public void processSuccess(String message) {
		logger.info("Exit assert processor: Success - Group: '{}' - Message: '{}'", getAssertGroup(), message);
	}

	@Override
	public void processFail(String message) {
		logger.info("Exit assert processor: Fail - Group: '{}' - Message: '{}'", getAssertGroup(), message);
		
		switch (exitReason) {
		case RunExitCalled:
			simulationController.exitRun();
			break;
		case ExecutionExitCalled:
			simulationController.exitExecution();			
			break;
		default:
			throw new RuntimeException(String.format("SimulationExitReason '{0} is not supported here.", exitReason));
		}
	}
}
