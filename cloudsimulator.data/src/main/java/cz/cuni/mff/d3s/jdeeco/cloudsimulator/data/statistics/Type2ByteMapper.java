package cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.statistics;

public interface Type2ByteMapper {
	byte convert(Class<?> type);
	Class<?> convert(byte type);
}
