package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.statistics.parsers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.statistics.RunStatistic;

// Provides what cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.StatisticsPersisterImpl persists
public class RunStatisticsParserImpl implements RunStatisticsParser {

	private final StatisticDataBuilderFactory statisticDataBuilderFactory;
	private final RunStatisticBuilderFactory runStatisticBuilderFactory;


	public RunStatisticsParserImpl(StatisticDataBuilderFactory statisticDataBuilderFactory,
			RunStatisticBuilderFactory runStatisticBuilderFactory) {
		this.statisticDataBuilderFactory = statisticDataBuilderFactory;
		this.runStatisticBuilderFactory = runStatisticBuilderFactory;
	}

	@Override
	public RunStatistic[] parse(String filePath) {
		RunStatisticBuilder runStatisticBuilder = runStatisticBuilderFactory.Create(); 
		StatisticDataBuilder statisticDataBuilder = statisticDataBuilderFactory.Create(runStatisticBuilder);

		try (FileReader fileReader = new FileReader(filePath); BufferedReader reader = new BufferedReader(fileReader)) {
			String line;
			while ((line = reader.readLine()) != null) {
				// process the line.
				statisticDataBuilder.processLine(line);
			}
		} catch (IOException e) {
			throw new ParseException("Cannot parse file. IO exception occurred.", e);
		}

		if (statisticDataBuilder.isInTerminalState()) {
			throw new ParseException("Last statistics is not correctly ended.");
		}

		return runStatisticBuilder.build();
	}
}