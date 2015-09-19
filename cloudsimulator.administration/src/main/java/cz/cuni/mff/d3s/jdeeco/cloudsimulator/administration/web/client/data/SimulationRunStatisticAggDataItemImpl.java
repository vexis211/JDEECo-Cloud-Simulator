package cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web.client.data;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.data.StatisticsSaveMode;

public class SimulationRunStatisticAggDataItemImpl<T> implements SimulationRunStatisticAggDataItem<T> {

	private final StatisticsSaveMode saveMode;
	private final T value;

	public SimulationRunStatisticAggDataItemImpl(StatisticsSaveMode saveMode, T value) {
		this.saveMode = saveMode;
		this.value = value;
	}
	
	@Override
	public StatisticsSaveMode getSaveMode() {
		return this.saveMode;
	}

	@Override
	public T getValue() {
		return this.value;
	}
}
