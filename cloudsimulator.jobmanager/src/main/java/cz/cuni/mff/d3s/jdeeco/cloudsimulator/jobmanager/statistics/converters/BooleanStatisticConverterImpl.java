package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.statistics.converters;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.statistics.RunStatisticFactory;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.statistics.parsers.ParsingParams;

class BooleanStatisticConverterImpl extends RunStatisticConverterImpl<Boolean> {

	public BooleanStatisticConverterImpl(Class<Boolean> clazz, RunStatisticFactory runStatisticFactory) {
		super(clazz, runStatisticFactory);
	}

	@Override
	protected Boolean[] convertVector(String stringValue) {
		String[] stringValues = stringValue.split(ParsingParams.VALUE_VALUE_DELIMITER);

		Boolean[] vector = new Boolean[stringValues.length];
		for (int i = 0; i < stringValues.length; i++) {
			vector[i] = Boolean.valueOf(stringValues[i]);
		}

		return vector;
	}

	@Override
	protected Boolean convertScalar(String value) {
		return Boolean.valueOf(value);
	}
}
