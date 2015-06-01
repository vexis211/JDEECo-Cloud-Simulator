package cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.logging;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.statistics.StatisticsManager;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.statistics.StatisticsHandler;

public class ProcessingStatisticsHandler implements StatisticsHandler {

	private StatisticsManager statisticsManager;

	public ProcessingStatisticsHandler(StatisticsManager statisticsManager) {
		this.statisticsManager = statisticsManager;
	}
	
	@Override
	public void write(String id, boolean value) {
		statisticsManager.getComparableProcessor(id, boolean.class).process(value);
	}

	@Override
	public void write(String id, byte value) {
		statisticsManager.getNumberProcessor(id, byte.class).process(value);
	}

	@Override
	public void write(String id, short value) {
		statisticsManager.getNumberProcessor(id, short.class).process(value);
	}

	@Override
	public void write(String id, int value) {
		statisticsManager.getNumberProcessor(id, int.class).process(value);
	}

	@Override
	public void write(String id, float value) {
		statisticsManager.getNumberProcessor(id, float.class).process(value);
	}

	@Override
	public void write(String id, double value) {
		statisticsManager.getNumberProcessor(id, double.class).process(value);
	}
}
