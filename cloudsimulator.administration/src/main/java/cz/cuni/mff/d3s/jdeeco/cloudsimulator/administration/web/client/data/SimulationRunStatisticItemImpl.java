package cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web.client.data;

import java.util.ArrayList;
import java.util.List;

public class SimulationRunStatisticItemImpl<T> implements SimulationRunStatisticItem<T> {

	private final long id;
	private final T[] vectorData;

	private SimulationRunItem simulationRun;
	private final List<SimulationRunStatisticAggDataItem<T>> aggDatas = new ArrayList<SimulationRunStatisticAggDataItem<T>>();

	public SimulationRunStatisticItemImpl(long id, T[] vectorData) {
		this.id = id;
		this.vectorData = vectorData;
	}

	@Override
	public long getId() {
		return this.id;
	}
	
	@Override
	public T[] getVectorData() {
		return this.vectorData;
	}

	@Override
	public SimulationRunItem getSimulationRun() {
		return this.simulationRun;
	}

	@Override
	public void setSimulationRun(SimulationRunItem simulationRun) {
		this.simulationRun = simulationRun;
	}

	@Override
	public List<SimulationRunStatisticAggDataItem<T>> getSimulationRunStatisticAggDatas() {
		return this.aggDatas;
	}

	@Override
	public void addSimulationRunStatisticAggData(SimulationRunStatisticAggDataItem<T> aggData) {
		this.aggDatas.add(aggData);
	}

}
