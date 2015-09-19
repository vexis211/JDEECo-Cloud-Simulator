package cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web.client.data;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.data.StatisticsSaveMode;

public interface SimulationRunStatisticAggDataItem<T> {
	StatisticsSaveMode getSaveMode();
	T getValue();
}
