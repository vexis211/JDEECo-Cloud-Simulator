package cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.statistics;

public interface StatisticsPersister<T> {
	void startStatistic(String statisticId);
	void endStatistic();

	void persistScalarValue(StatisticsSaveMode mode, Object value);

	void startPersistingVector(StatisticsSaveMode mode);
	void persistVectorValue(Object value);
	void endPersistingVector();
}
