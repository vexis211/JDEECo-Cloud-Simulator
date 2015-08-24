package cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.statistics;

import java.io.PrintStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.data.StatisticsSaveMode;

// Persists what cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.results.StatisticsProviderImpl will provide.
public class FileStatisticsPersisterImpl<T> implements StatisticsPersister {

	private static final char END_STATISTIC_CHAR = '@';
	private static final char KEY_VALUE_DELIMITER = '|';
	private static final char VALUE_VALUE_DELIMITER = ',';
	
	private static Logger logger = LoggerFactory.getLogger(FileStatisticsPersisterImpl.class);
	
	private final String persistFile;
	private PrintStream output;
	
	public FileStatisticsPersisterImpl(String persistFile) {
		this.persistFile = persistFile;
	}

	@Override
	public void start() {
		try {
			logger.info("Opening file for persisting statistics. File name: '{}'.", persistFile);
			this.output = new PrintStream(persistFile);
		} catch (Exception e) {
			logger.error("Error occured when starting persisting statistics.", e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public void end() {
		try {
			logger.info("Closing file for persisting statistics. File name: '{}'.", persistFile);
			this.output.close();
		} catch (Exception e) {
			logger.error("Error occured when ending persisting statistics.", e);
			throw e;
		}
	}
	
	@Override
	public void startStatistic(String statisticId, Class<?> clazz) {
		logger.trace("Starting persistance of statistic with name '{}' and type '{}'.", statisticId, clazz);
		
		output.println(statisticId);
		output.println(clazz.getName());
	}

	@Override
	public void endStatistic() {
		logger.trace("Ending persistance of last opened statistic.");
		
		output.println(END_STATISTIC_CHAR);
	}

	@Override
	public void addScalarValue(StatisticsSaveMode mode, Object value) {
		logger.trace("Persisting statistic value. Mode: '{}', value: '{}'.", mode, value);
		
		output.print(mode);
		output.print(KEY_VALUE_DELIMITER);
		output.print(value);
		output.println();
	}

	@Override
	public void startVector() {
		logger.trace("Starting persistance of statistic vector.");
		
		output.print(StatisticsSaveMode.Vector);
		output.print(KEY_VALUE_DELIMITER);
	}

	@Override
	public void addVectorValue(Object value) {
		logger.trace("Statistic vector value: '{}'.", value);
		
		output.print(value);
		output.print(VALUE_VALUE_DELIMITER);
	}

	@Override
	public void endVector() {
		logger.trace("Ending persistance of statistic vector.");
		
		output.println();
	}
}
