package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.statistics.converters;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.statistics.RunStatisticFactory;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.statistics.parsers.ParsingParams;

class LongStatisticConverterImpl extends RunStatisticConverterImpl<Long> {

	public LongStatisticConverterImpl(Class<Long> clazz, RunStatisticFactory runStatisticFactory) {
		super(clazz, runStatisticFactory);
	}

	@Override
	protected Long[] convertVector(String stringValue) {
		String[] stringValues = stringValue.split(ParsingParams.VALUE_VALUE_DELIMITER);

		Long[] vector = new Long[stringValues.length];
		for (int i = 0; i < stringValues.length; i++) {
			vector[i] = Long.valueOf(stringValues[i]);
		}

		return vector;
	}

	@Override
	protected Long convertScalar(String value) {
		return Long.valueOf(value);
	}
}
