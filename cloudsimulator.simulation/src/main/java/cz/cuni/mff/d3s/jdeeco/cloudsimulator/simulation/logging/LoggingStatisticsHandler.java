package cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.logging;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.statistics.StatisticsHandler;

public class LoggingStatisticsHandler implements StatisticsHandler {

	private static Logger logger = LogManager.getLogger(LoggingStatisticsHandler.class);

	private void writeInternal(String id, String value) {
		logger.info(String.format("New statistic - ID: '%s' - Value: '%s'", id, value));
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
	public void write(String id, float value) {
		writeInternal(id, String.valueOf(value));
	}

	@Override
	public void write(String id, double value) {
		writeInternal(id, String.valueOf(value));
	}
}
