package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.results;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class StatisticsProviderImpl implements StatisticsProvider {

	private static final char END_STATISTIC_CHAR = '@';
	private static final char KEY_VALUE_DELIMITER = '|';
	private static final char VALUE_VALUE_DELIMITER = ',';

	private static Logger logger = LogManager.getLogger(StatisticsProviderImpl.class);

	private final String inputFile;

	public StatisticsProviderImpl(String inputFile) {
		this.inputFile = inputFile;
	}
}
