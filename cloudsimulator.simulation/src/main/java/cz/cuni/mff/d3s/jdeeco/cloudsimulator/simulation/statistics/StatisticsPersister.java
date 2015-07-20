package cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.statistics;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.data.StatisticsSaveMode;

public interface StatisticsPersister {
	void start();
	void end();
	
	void startStatistic(String statisticId, Class<?> clazz);
	void endStatistic();

	void addScalarValue(StatisticsSaveMode mode, Object value);

	void startVector(StatisticsSaveMode mode);
	void addVectorValue(Object value);
	void endVector();
}
