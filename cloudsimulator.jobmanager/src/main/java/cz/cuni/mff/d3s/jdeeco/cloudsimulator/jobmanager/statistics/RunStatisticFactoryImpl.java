package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.statistics;

import java.util.Map;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.data.StatisticsSaveMode;

public class RunStatisticFactoryImpl implements RunStatisticFactory {

	@Override
	public <T> RunStatistic create(String name, Map<StatisticsSaveMode, T> aggregatedValues, T[] valuesVector) {
		return new RunStatisticImpl<T>(name, aggregatedValues, valuesVector);
	}
}
