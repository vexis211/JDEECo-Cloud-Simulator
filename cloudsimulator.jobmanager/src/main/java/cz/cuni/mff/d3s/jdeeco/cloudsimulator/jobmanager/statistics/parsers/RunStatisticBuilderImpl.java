package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.statistics.parsers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.statistics.RunStatistic;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.statistics.converters.RunStatisticConverter;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.statistics.converters.RunStatisticConverterFactory;

class RunStatisticBuilderImpl implements RunStatisticBuilder {

	private final Map<Class<?>, RunStatisticConverter> converters = new HashMap<Class<?>, RunStatisticConverter>();
	private final List<RunStatistic> statistics = new ArrayList<RunStatistic>();

	private final RunStatisticConverterFactory runStatisticConverterFactory;
	
	
	public RunStatisticBuilderImpl(RunStatisticConverterFactory runStatisticConverterFactory) {
		this.runStatisticConverterFactory = runStatisticConverterFactory;
	}

	@Override
	public void statisticDataBuilt(StatisticData data) {
		Class<?> dataClass = data.getValueClass();
		RunStatisticConverter converter;
		if (converters.containsKey(dataClass)) {
			converter = converters.get(dataClass);
		} else {
			converter = runStatisticConverterFactory.create(dataClass);
			converters.put(dataClass, converter);
		}

		RunStatistic newStatistic = converter.convert(data);
		statistics.add(newStatistic);
	}

	@Override
	public RunStatistic[] build() {
		return statistics.toArray(new RunStatistic[statistics.size()]);
	}
}
