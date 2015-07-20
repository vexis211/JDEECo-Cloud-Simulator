package cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.statistics;

public interface ByteArrayConverterProvider {
	<T> ByteArrayConverter<T> get(Class<T> valueClass);
}
