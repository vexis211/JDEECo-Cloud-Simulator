package cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.statistics;

import java.io.PrintStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Persists what cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.results.StatisticsProviderImpl will provide.
public class StatisticsPersisterImpl<T> implements StatisticsPersister {

	private static final char END_STATISTIC_CHAR = '@';
	private static final char KEY_VALUE_DELIMITER = '|';
	private static final char VALUE_VALUE_DELIMITER = ',';
	
	private static Logger logger = LoggerFactory.getLogger(StatisticsPersisterImpl.class);
	
	private final String persistFile;
	private PrintStream output;
	
	public StatisticsPersisterImpl(String persistFile) {
		this.persistFile = persistFile;
	}

	@Override
	public void start() {
		try {
			this.output = new PrintStream(persistFile);
		} catch (Exception e) {
			logger.error("Error occured when starting persisting statistics.", e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public void end() {
		try {
			this.output.close();
		} catch (Exception e) {
			logger.error("Error occured when ending persisting statistics.", e);
			throw e;
		}
	}
	
	@Override
	public void startStatistic(String statisticId) {
		output.println(statisticId);
	}

	@Override
	public void endStatistic() {
		output.println(END_STATISTIC_CHAR);
	}

	@Override
	public void addScalarValue(StatisticsSaveMode mode, Object value) {
		output.print(mode);
		output.print(KEY_VALUE_DELIMITER);
		output.print(value);
		output.println();
	}

	@Override
	public void startVector(StatisticsSaveMode mode) {
		output.print(mode);
		output.print(KEY_VALUE_DELIMITER);
	}

	@Override
	public void addVectorValue(Object value) {
		output.print(value);
		output.print(VALUE_VALUE_DELIMITER);
	}

	@Override
	public void endVector() {
		output.println();
	}
}
