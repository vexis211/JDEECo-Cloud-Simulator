package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.statistics.converters;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.statistics.RunStatisticFactory;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.statistics.parsers.ParsingParams;

class IntStatisticConverterImpl extends RunStatisticConverterImpl<Integer> {

	public IntStatisticConverterImpl(Class<Integer> clazz, RunStatisticFactory runStatisticFactory) {
		super(clazz, runStatisticFactory);
	}

	@Override
	protected Integer[] convertVector(String stringValue) {
		String[] stringValues = stringValue.split(ParsingParams.VALUE_VALUE_DELIMITER);

		Integer[] vector = new Integer[stringValues.length];
		for (int i = 0; i < stringValues.length; i++) {
			vector[i] = Integer.valueOf(stringValues[i]);
		}

		return vector;
	}

	@Override
	protected Integer convertScalar(String value) {
		return Integer.valueOf(value);
	}
}
