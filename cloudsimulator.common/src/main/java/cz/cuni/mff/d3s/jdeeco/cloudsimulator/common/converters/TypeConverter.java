package cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.converters;

public interface TypeConverter {
	<TFrom, TTo> TTo convert(TFrom value, Class<TTo> clazz);
}
