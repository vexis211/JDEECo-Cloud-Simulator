package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.results.providers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


// Provides what cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.StatisticsPersisterImpl persists
public class StatisticsProviderImpl implements StatisticsProvider {

	private static Logger logger = LoggerFactory.getLogger(StatisticsProviderImpl.class);

	private final String inputFile;

	public StatisticsProviderImpl(String inputFile) {
		this.inputFile = inputFile;
	}
}
