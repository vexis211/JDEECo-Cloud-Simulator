package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.statistics.converters;

import java.util.HashMap;
import java.util.Map;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.data.StatisticsSaveMode;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.statistics.RunStatistic;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.statistics.RunStatisticFactory;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.statistics.parsers.StatisticData;

abstract class RunStatisticConverterImpl<T> implements RunStatisticConverter {

	private final RunStatisticFactory runStatisticFactory;
	private final Class<T> clazz;
	
	public RunStatisticConverterImpl(Class<T> clazz, RunStatisticFactory runStatisticFactory) {
		this.clazz = clazz;
		this.runStatisticFactory = runStatisticFactory;
	}

	@Override
	public RunStatistic convert(StatisticData data) {
				
		Map<StatisticsSaveMode, T> aggregatedValues = null;
		T[] valuesVector = null;
		
		for (Map.Entry<StatisticsSaveMode, String> entry : data.getValues().entrySet()) {
			if (entry.getKey() ==StatisticsSaveMode.Vector) {
				valuesVector = convertVector(entry.getValue());
			} else {
				if (aggregatedValues == null) {
					aggregatedValues = new HashMap<>();
				}
				T value = convertScalar(entry.getValue());
				aggregatedValues.put(entry.getKey(), value);
			}
		}
		
		return runStatisticFactory.create(data.getName(), clazz, aggregatedValues, valuesVector);
	}

	protected abstract T[] convertVector(String stringValue);
	protected abstract T convertScalar(String value);
}
