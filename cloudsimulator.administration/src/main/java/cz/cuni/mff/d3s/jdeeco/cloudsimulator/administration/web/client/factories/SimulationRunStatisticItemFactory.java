package cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web.client.factories;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web.client.data.SimulationRunStatisticItem;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.models.SimulationRunStatistic;

public interface SimulationRunStatisticItemFactory {
	<T> SimulationRunStatisticItem<T> create(SimulationRunStatistic runStatistic, Class<T> clazz, boolean setRun, boolean addAggDatas);
}
