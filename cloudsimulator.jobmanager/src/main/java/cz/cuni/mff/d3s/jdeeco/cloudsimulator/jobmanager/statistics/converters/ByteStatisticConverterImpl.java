package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.statistics.converters;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.statistics.RunStatisticFactory;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.statistics.parsers.ParsingParams;

class ByteStatisticConverterImpl extends RunStatisticConverterImpl<Byte> {

	public ByteStatisticConverterImpl(Class<Byte> clazz, RunStatisticFactory runStatisticFactory) {
		super(clazz, runStatisticFactory);
	}

	@Override
	protected Byte[] convertVector(String stringValue) {
		String[] stringValues = stringValue.split(ParsingParams.VALUE_VALUE_DELIMITER);

		Byte[] vector = new Byte[stringValues.length];
		for (int i = 0; i < stringValues.length; i++) {
			vector[i] = Byte.valueOf(stringValues[i]);
		}

		return vector;
	}

	@Override
	protected Byte convertScalar(String value) {
		return Byte.valueOf(value);
	}
}
