package cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web.client.factories;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.models.SimulationData;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web.client.data.SimulationDataItem;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web.client.data.SimulationDataItemImpl;

public class SimulationDataItemFactoryImpl implements SimulationDataItemFactory {

	@Override
	public SimulationDataItem create(SimulationData data) {
	
		SimulationDataItem newDataItem = new SimulationDataItemImpl(data);

		return newDataItem;
	}
}
