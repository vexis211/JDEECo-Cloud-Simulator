package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.statistics.converters;

public interface RunStatisticConverterFactory {
	<T> RunStatisticConverter create(Class<T> clazz);
}
