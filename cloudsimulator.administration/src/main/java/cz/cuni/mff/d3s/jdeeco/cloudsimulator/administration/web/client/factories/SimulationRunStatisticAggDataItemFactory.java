package cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web.client.factories;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web.client.data.SimulationRunStatisticAggDataItem;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.models.SimulationRunStatisticAggdata;

public interface SimulationRunStatisticAggDataItemFactory {
	<T> SimulationRunStatisticAggDataItem<T> create(SimulationRunStatisticAggdata aggData, Class<T> clazz);
}
