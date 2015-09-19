package cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.converters;

public interface ConcreteTypeConverter<TFrom, TTo> {
	Class<TFrom> getFromClass();
	Class<TTo> getToClass();
	
	TTo convert(TFrom value);
}
