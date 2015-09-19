package cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web.client.factories;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web.client.data.SimulationExecutionStatisticGenericItem;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.models.SimulationExecutionStatistic;

public interface SimulationExecutionStatisticItemFactory {
	<T> SimulationExecutionStatisticGenericItem<T> create(SimulationExecutionStatistic executionStatistic, Class<T> clazz,
			boolean setExecution, boolean addRunStatistics);
}
