package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.statistics.converters;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.statistics.RunStatisticFactory;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.statistics.parsers.ParsingParams;

class FloatStatisticConverterImpl extends RunStatisticConverterImpl<Float> {

	public FloatStatisticConverterImpl(Class<Float> clazz, RunStatisticFactory runStatisticFactory) {
		super(clazz, runStatisticFactory);
	}

	@Override
	protected Float[] convertVector(String stringValue) {
		String[] stringValues = stringValue.split(ParsingParams.VALUE_VALUE_DELIMITER);

		Float[] vector = new Float[stringValues.length];
		for (int i = 0; i < stringValues.length; i++) {
			vector[i] = Float.valueOf(stringValues[i]);
		}

		return vector;
	}

	@Override
	protected Float convertScalar(String value) {
		return Float.valueOf(value);
	}
}
