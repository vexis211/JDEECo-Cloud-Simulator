package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.statistics.parsers;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.data.StatisticsSaveMode;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.data.StatisticsSaveModesParser;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.statistics.parsers.ParsingParams;

class StatisticsDataBuilderImpl implements StatisticDataBuilder {

	private final StatisticDataBuilderListener listener;

	private LineProcessor currentProccessor = x -> processStatName(x);
	private StatisticData currentData;

	public StatisticsDataBuilderImpl(StatisticDataBuilderListener listener) {
		this.listener = listener;
	}

	@Override
	public boolean isInTerminalState() {
		return currentData == null;
	}

	@Override
	public void processLine(String line) {
		currentProccessor.processLine(line);
	}

	private void processStatName(String line) {
		currentData = new StatisticData(line);
		currentProccessor = x -> processValueClass(x);
	}

	private void processValueClass(String line) {
		try {
			currentData.setValueClass(Class.forName(line));
			currentProccessor = x -> processValueOrEnd(x);
		} catch (ClassNotFoundException e) {
			throw new ParseException("Cannot parse value class.", e);
		}
	}

	private void processValueOrEnd(String line) {
		if (line != ParsingParams.END_STATISTIC_STRING) {
			processValue(line);
		} else {
			end();
		}
	}

	private void processValue(String line) {
		String[] parts = line.split(ParsingParams.KEY_VALUE_DELIMITER);
		StatisticsSaveMode saveMode = StatisticsSaveModesParser.parseSaveMode(parts[0]);
		currentData.setValue(saveMode, parts[1]);
	}

	private void end() {
		listener.statisticDataBuilt(currentData);
		currentData = null;
		currentProccessor = x -> processStatName(x);
	}

	interface LineProcessor {
		void processLine(String line);
	}
}
