package cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web.client.data;

import java.util.ArrayList;
import java.util.List;

public class SimulationExecutionStatisticItemImpl<T> implements SimulationExecutionStatisticGenericItem<T> {

	private final int id;
	private final String name;
	private final String dataType;

	private SimulationExecutionItem execution;
	private List<SimulationRunStatisticItem<T>> runStatistics = new ArrayList<SimulationRunStatisticItem<T>>();

	public SimulationExecutionStatisticItemImpl(int id, String name, Class<T> clazz) {
		this.id = id;
		this.name = name;
		this.dataType = clazz.getName();
	}

	@Override
	public int getId() {
		return this.id;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public String getDataType() {
		return this.dataType;
	}

	@Override
	public SimulationExecutionItem getSimulationExecution() {
		return this.execution;
	}

	@Override
	public void setSimulationExecution(SimulationExecutionItem execution) {
		this.execution = execution;
	}

	@Override
	public List<SimulationRunStatisticItem<T>> getRunStatistics() {
		return this.runStatistics;
	}

	@Override
	public void addRunStatistic(SimulationRunStatisticItem<T> runStatistic) {
		this.runStatistics.add(runStatistic);
	}
}
