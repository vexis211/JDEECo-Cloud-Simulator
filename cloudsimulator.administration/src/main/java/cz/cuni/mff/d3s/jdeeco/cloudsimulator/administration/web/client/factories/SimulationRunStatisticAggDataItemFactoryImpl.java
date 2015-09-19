package cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web.client.factories;

import javax.annotation.Resource;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web.client.data.SimulationRunStatisticAggDataItem;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web.client.data.SimulationRunStatisticAggDataItemImpl;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.models.SimulationRunStatisticAggdata;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.statistics.ByteArrayConverter;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.statistics.ByteArrayConverterProvider;

public class SimulationRunStatisticAggDataItemFactoryImpl implements SimulationRunStatisticAggDataItemFactory {

	@Resource
	private ByteArrayConverterProvider byteArrayConverterProvider;
	
	@Override
	public <T> SimulationRunStatisticAggDataItem<T> create(SimulationRunStatisticAggdata aggData, Class<T> clazz) {
		byte[] valueInBytes = aggData.getData();
		ByteArrayConverter<T> converter = byteArrayConverterProvider.get(clazz);
		T value = converter.convertBackScalar(valueInBytes);
		return new SimulationRunStatisticAggDataItemImpl<T>(aggData.getId().getSaveType(), value);
	}
}
