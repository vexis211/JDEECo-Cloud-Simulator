package cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.statistics;

public class ByteArrayConverterProviderImpl implements ByteArrayConverterProvider{
	
	@Override
	public <T> ByteArrayConverter<T> get(Class<T> valueClass) {
		return new ByteArrayConverterImpl<T>(valueClass);
	}
}
