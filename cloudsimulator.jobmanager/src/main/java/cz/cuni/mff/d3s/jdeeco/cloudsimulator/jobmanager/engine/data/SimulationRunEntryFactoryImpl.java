package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.data;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.models.SimulationRun;

public class SimulationRunEntryFactoryImpl implements SimulationRunEntryFactory {

	@Override
	public SimulationRunEntry create(SimulationRun data) {
		return new SimulationRunEntryImpl(data);
	}
}
