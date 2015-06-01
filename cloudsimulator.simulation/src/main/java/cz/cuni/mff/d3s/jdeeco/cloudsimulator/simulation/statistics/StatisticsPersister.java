package cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.statistics;

public interface StatisticsPersister {
	void start();
	void end();
	
	void startStatistic(String statisticId);
	void endStatistic();

	void addScalarValue(StatisticsSaveMode mode, Object value);

	void startVector(StatisticsSaveMode mode);
	void addVectorValue(Object value);
	void endVector();
}
