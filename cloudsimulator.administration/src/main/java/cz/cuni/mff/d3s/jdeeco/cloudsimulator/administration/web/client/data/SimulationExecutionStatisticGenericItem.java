package cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web.client.data;

import java.util.List;

public interface SimulationExecutionStatisticGenericItem<T> extends SimulationExecutionStatisticItem {
	
	List<SimulationRunStatisticItem<T>> getRunStatistics();	
	void addRunStatistic(SimulationRunStatisticItem<T> runStatistic);
}
