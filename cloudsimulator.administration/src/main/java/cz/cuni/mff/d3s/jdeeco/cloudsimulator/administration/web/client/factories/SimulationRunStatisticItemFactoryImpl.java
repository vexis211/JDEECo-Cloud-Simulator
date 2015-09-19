package cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web.client.factories;

import javax.annotation.Resource;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web.client.data.SimulationRunItem;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web.client.data.SimulationRunStatisticItem;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web.client.data.SimulationRunStatisticItemImpl;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.models.SimulationRunStatistic;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.statistics.ByteArrayConverter;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.statistics.ByteArrayConverterProvider;

public class SimulationRunStatisticItemFactoryImpl implements SimulationRunStatisticItemFactory {

	@Resource
	private ByteArrayConverterProvider byteArrayConverterProvider;

	@Resource
	private SimulationRunItemFactory runItemFactory;

	@Resource
	private SimulationRunStatisticAggDataItemFactory runStatisticAggDataItemFactory;

	@Override
	public <T> SimulationRunStatisticItem<T> create(SimulationRunStatistic runStatistic, Class<T> clazz, boolean setRun,
			boolean addAggDatas) {
		// get vector data
		byte[] vectorInBytes = runStatistic.getVectorData();
		ByteArrayConverter<T> converter = byteArrayConverterProvider.get(clazz);
		T[] vector = converter.convertBackVector(vectorInBytes);
		// create item
		SimulationRunStatisticItem<T> runStatisticItem = new SimulationRunStatisticItemImpl<T>(runStatistic.getId(),
				vector);

		if (setRun) {
			SimulationRunItem runItem = runItemFactory.create(runStatistic.getSimulationRun());
			runStatisticItem.setSimulationRun(runItem);
		}
		if (addAggDatas) {
			runStatistic.getSimulationRunStatisticAggdatas().stream()
					.sorted((d1, d2) -> d2.getId().getSaveType().compareTo(d1.getId().getSaveType()))
					.forEach(d -> runStatisticItem.addSimulationRunStatisticAggData(
							runStatisticAggDataItemFactory.create(d, clazz)));
		}

		return runStatisticItem;
	}
}
