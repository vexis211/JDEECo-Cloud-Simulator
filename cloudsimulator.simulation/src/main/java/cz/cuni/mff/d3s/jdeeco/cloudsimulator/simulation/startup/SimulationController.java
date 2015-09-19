package cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.startup;

import cz.cuni.mff.d3s.deeco.runners.DEECoSimulation;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.data.SimulationExitReason;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.settings.SimulationEndSettings;

public interface SimulationController {
	SimulationExitReason start(DEECoSimulation simulation, SimulationEndSettings endSettings) throws Exception;
	
	void exitRun();
	void exitExecution();
}
