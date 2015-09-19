package cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.converters;

public interface String2ClassConverter {
	Class<?> convert(String typeString);
	String convert(Class<?> type);
}
