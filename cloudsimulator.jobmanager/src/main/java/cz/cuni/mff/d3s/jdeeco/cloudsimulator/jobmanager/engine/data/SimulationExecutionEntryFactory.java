package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.data;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.models.SimulationExecution;

public interface SimulationExecutionEntryFactory {
	
	SimulationExecutionEntry create(SimulationExecution data, SimulationExecutionEntryListener listener);
}
