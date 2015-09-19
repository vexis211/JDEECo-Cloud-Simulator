package cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web.client.factories;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web.client.data.SimulationRunItem;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web.client.data.SimulationRunItemImpl;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.models.SimulationRun;

public class SimulationRunItemFactoryImpl implements SimulationRunItemFactory {

	@Override
	public SimulationRunItem create(SimulationRun run) {
		SimulationRunItem runItem = new SimulationRunItemImpl(run);
		return runItem;
	}
}
