package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.models.SimulationRun;

public class SimulationRunEntryFactoryImpl implements SimulationRunEntryFactory {

	@Override
	public SimulationRunEntry create(SimulationRun data, SimulationExecutionEntry executionEntry) {
		return new SimulationRunEntryImpl(data, executionEntry);
	}
}
