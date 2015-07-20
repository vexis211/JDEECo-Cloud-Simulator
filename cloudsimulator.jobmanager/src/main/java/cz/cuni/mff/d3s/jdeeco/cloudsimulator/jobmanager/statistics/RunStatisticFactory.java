package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.statistics;

import java.util.Map;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.data.StatisticsSaveMode;

public interface RunStatisticFactory {
	<T> RunStatistic create(String name, Class<T> valueClass, Map<StatisticsSaveMode, T> aggregatedValues, T[] valuesVector);
}
