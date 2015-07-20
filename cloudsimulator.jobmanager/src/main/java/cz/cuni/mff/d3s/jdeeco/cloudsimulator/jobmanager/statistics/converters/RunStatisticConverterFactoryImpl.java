package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.statistics.converters;

import java.util.HashMap;
import java.util.Map;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.statistics.RunStatisticFactory;

class RunStatisticConverterFactoryImpl implements RunStatisticConverterFactory {

	private final Map<Class<?>, RunStatisticConverter> converters = new HashMap<>();

	public RunStatisticConverterFactoryImpl(RunStatisticFactory runStatisticFactory) {
		this.converters.put(boolean.class, new BooleanStatisticConverterImpl(boolean.class, runStatisticFactory));
		this.converters.put(byte.class, new ByteStatisticConverterImpl(byte.class, runStatisticFactory));
		this.converters.put(short.class, new ShortStatisticConverterImpl(short.class, runStatisticFactory));
		this.converters.put(int.class, new IntStatisticConverterImpl(int.class, runStatisticFactory));
		this.converters.put(long.class, new LongStatisticConverterImpl(long.class, runStatisticFactory));
		this.converters.put(float.class, new FloatStatisticConverterImpl(float.class, runStatisticFactory));
		this.converters.put(double.class, new DoubleStatisticConverterImpl(double.class, runStatisticFactory));
	}

	@Override
	public <T> RunStatisticConverter create(Class<T> clazz) {
		return converters.get(clazz);
	}
}