package cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.statistics;

public interface ByteArrayConverter<T> {

	byte[] convertVector(T[] valuesVector);
	byte[] convertScalar(T value);

	T[] convertBackVector(byte[] bytes);
	T convertBackScalar(byte[] bytes);
}
