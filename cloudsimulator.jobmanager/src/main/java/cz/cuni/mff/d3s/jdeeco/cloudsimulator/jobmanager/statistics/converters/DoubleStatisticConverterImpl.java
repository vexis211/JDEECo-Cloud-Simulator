package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.statistics.converters;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.statistics.RunStatisticFactory;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.statistics.parsers.ParsingParams;

class DoubleStatisticConverterImpl extends RunStatisticConverterImpl<Double> {

	public DoubleStatisticConverterImpl(Class<Double> clazz, RunStatisticFactory runStatisticFactory) {
		super(clazz, runStatisticFactory);
	}

	@Override
	protected Double[] convertVector(String stringValue) {
		String[] stringValues = stringValue.split(ParsingParams.VALUE_VALUE_DELIMITER);

		Double[] vector = new Double[stringValues.length];
		for (int i = 0; i < stringValues.length; i++) {
			vector[i] = Double.valueOf(stringValues[i]);
		}

		return vector;
	}

	@Override
	protected Double convertScalar(String value) {
		return Double.valueOf(value);
	}
}
