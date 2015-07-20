package cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.statistics.StatisticsHandler;

public class LoggingStatisticsHandler implements StatisticsHandler {

	private static Logger logger = LoggerFactory.getLogger(LoggingStatisticsHandler.class);

	private void writeInternal(String id, String value) {
		logger.info("New statistic - ID: '{}' - Value: '{}'", id, value);
	}

	@Override
	public void write(String id) {
		writeInternal(id, "");
	}
	
	@Override
	public void write(String id, boolean value) {
		writeInternal(id, String.valueOf(value));
	}

	@Override
	public void write(String id, byte value) {
		writeInternal(id, String.valueOf(value));
	}

	@Override
	public void write(String id, short value) {
		writeInternal(id, String.valueOf(value));
	}

	@Override
	public void write(String id, int value) {
		writeInternal(id, String.valueOf(value));
	}

	@Override
	public void write(String id, long value) {
		writeInternal(id, String.valueOf(value));
	}

	@Override
	public void write(String id, float value) {
		writeInternal(id, String.valueOf(value));
	}

	@Override
	public void write(String id, double value) {
		writeInternal(id, String.valueOf(value));
	}
}
