package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.results.providers;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Provides what cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.StatisticsPersisterImpl persists
public class RunStatisticsProviderImpl implements RunStatisticsProvider {

	private static final char END_STATISTIC_CHAR = '@';
	private static final char KEY_VALUE_DELIMITER = '|';
	private static final char VALUE_VALUE_DELIMITER = ',';

	private static Logger logger = LoggerFactory.getLogger(RunStatisticsProviderImpl.class);

	@Override
	public RunStatistics Get(String file) {

		try (FileReader fileReader = new FileReader(file); BufferedReader reader = new BufferedReader(fileReader)) {

			String line;
			while ((line = reader.readLine()) != null) {
				// process the line.
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO OOOOOOOOOOOOOOOOOOOOOOOOOOOOO
		return null;
	}
}
