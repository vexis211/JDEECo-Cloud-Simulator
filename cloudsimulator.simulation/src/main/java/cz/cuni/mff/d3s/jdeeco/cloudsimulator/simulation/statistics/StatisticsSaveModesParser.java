package cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.statistics;

import java.util.EnumSet;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class StatisticsSaveModesParser {

	private static final Logger logger = LogManager.getLogger(StatisticsSaveModesParser.class);

	public static final EnumSet<StatisticsSaveMode> parseSaveModes(String saveModesString) {
		EnumSet<StatisticsSaveMode> modes = EnumSet.noneOf(StatisticsSaveMode.class);

		if (saveModesString == null || saveModesString.length() == 0) {
			return modes;
		}

		String[] saveModeStringsList = saveModesString.split(",");
		for (String part : saveModeStringsList) {
			StatisticsSaveMode parsedMode = parseSaveMode(part);

			if (parsedMode == null) {
				logger.error(String.format(
						"'%s' cannot be converted to StatisticsSaveMode. Whole save mode string: '%s'.", part,
						saveModesString));
			} else if (modes.contains(parsedMode)) {
				logger.error(String.format(
						"In save mode string there is '%s' specified multiple times. Whole save mode string: '%s'.",
						parsedMode, saveModesString));
			} else {
				modes.add(parsedMode);
			}
		}

		return modes;
	}

	public static final StatisticsSaveMode parseSaveMode(String saveMode) {
		for (StatisticsSaveMode mode : EnumSet.allOf(StatisticsSaveMode.class)) {
			if (mode.name().equalsIgnoreCase(saveMode)) {
				return mode;
			}
		}

		return null;
	}

}
