package cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web.client.factories;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.models.SimulationData;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web.client.data.SimulationDataItem;

public interface SimulationDataItemFactory {

	public SimulationDataItem create(SimulationData data);
}
