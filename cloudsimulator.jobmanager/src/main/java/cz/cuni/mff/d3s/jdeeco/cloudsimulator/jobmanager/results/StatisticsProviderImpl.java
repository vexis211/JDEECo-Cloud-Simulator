package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.results;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StatisticsProviderImpl implements StatisticsProvider {

	private static final char END_STATISTIC_CHAR = '@';
	private static final char KEY_VALUE_DELIMITER = '|';
	private static final char VALUE_VALUE_DELIMITER = ',';

	private static Logger logger = LoggerFactory.getLogger(StatisticsProviderImpl.class);

	private final String inputFile;

	public StatisticsProviderImpl(String inputFile) {
		this.inputFile = inputFile;
	}
}
