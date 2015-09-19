package cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web.client.factories;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web.client.data.SimulationRunItem;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.models.SimulationRun;

public interface SimulationRunItemFactory {

	public SimulationRunItem create(SimulationRun run);
}
