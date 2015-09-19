package cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web.client.factories;

import javax.annotation.Resource;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web.client.data.SimulationExecutionItem;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web.client.data.SimulationExecutionStatisticGenericItem;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web.client.data.SimulationExecutionStatisticItemImpl;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.models.SimulationExecutionStatistic;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.statistics.ByteArrayConverterProvider;

public class SimulationExecutionStatisticItemFactoryImpl implements SimulationExecutionStatisticItemFactory {

	@Resource
	private ByteArrayConverterProvider byteArrayConverterProvider;

	@Resource
	private SimulationExecutionItemFactory executionItemFactory;

	@Resource
	private SimulationRunStatisticItemFactory runStatisticItemFactory;

	@Override
	public <T> SimulationExecutionStatisticGenericItem<T> create(SimulationExecutionStatistic executionStatistic, Class<T> clazz,
			boolean setExecution, boolean addRunStatistics) {

		SimulationExecutionStatisticGenericItem<T> executionStatisticItem = new SimulationExecutionStatisticItemImpl<T>(
				executionStatistic.getId(), executionStatistic.getName(), clazz);

		if (setExecution) {
			SimulationExecutionItem executionItem = executionItemFactory
					.create(executionStatistic.getSimulationExecution());
			executionStatisticItem.setSimulationExecution(executionItem);
		}
		if (addRunStatistics) {
			executionStatistic.getSimulationRunStatistics().stream().forEach(r -> executionStatisticItem
					.addRunStatistic(runStatisticItemFactory.create(r, clazz, false, addRunStatistics)));
		}

		return executionStatisticItem;
	}
}
