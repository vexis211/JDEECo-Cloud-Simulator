package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.statistics.converters;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.statistics.RunStatisticFactory;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.statistics.parsers.ParsingParams;

class ShortStatisticConverterImpl extends RunStatisticConverterImpl<Short> {

	public ShortStatisticConverterImpl(Class<Short> clazz, RunStatisticFactory runStatisticFactory) {
		super(clazz, runStatisticFactory);
	}

	@Override
	protected Short[] convertVector(String stringValue) {
		String[] stringValues = stringValue.split(ParsingParams.VALUE_VALUE_DELIMITER);

		Short[] vector = new Short[stringValues.length];
		for (int i = 0; i < stringValues.length; i++) {
			vector[i] = Short.valueOf(stringValues[i]);
		}

		return vector;
	}

	@Override
	protected Short convertScalar(String value) {
		return Short.valueOf(value);
	}
}
