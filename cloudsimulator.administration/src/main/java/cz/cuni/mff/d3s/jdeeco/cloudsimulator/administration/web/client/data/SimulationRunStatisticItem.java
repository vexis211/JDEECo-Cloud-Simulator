package cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web.client.data;

import java.util.List;

public interface SimulationRunStatisticItem<T> {

	long getId();
	
	T[] getVectorData();
		
	SimulationRunItem getSimulationRun();
	void setSimulationRun(SimulationRunItem simulationRun);

	List<SimulationRunStatisticAggDataItem<T>> getSimulationRunStatisticAggDatas();
	void addSimulationRunStatisticAggData(SimulationRunStatisticAggDataItem<T> aggData);
}
