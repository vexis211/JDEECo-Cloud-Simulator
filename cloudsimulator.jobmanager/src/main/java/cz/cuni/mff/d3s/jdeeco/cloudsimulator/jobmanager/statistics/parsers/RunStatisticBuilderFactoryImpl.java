package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.statistics.parsers;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.statistics.converters.RunStatisticConverterFactory;

public class RunStatisticBuilderFactoryImpl implements RunStatisticBuilderFactory {

	private final RunStatisticConverterFactory runStatisticConverterFactory;

	public RunStatisticBuilderFactoryImpl(RunStatisticConverterFactory runStatisticConverterFactory) {
		this.runStatisticConverterFactory = runStatisticConverterFactory;
	}

	@Override
	public RunStatisticBuilder Create() {
		return new RunStatisticBuilderImpl(runStatisticConverterFactory);
	}

}
