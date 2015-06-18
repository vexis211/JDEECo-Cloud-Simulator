package cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.statistics;

import java.util.EnumSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StatisticsSaveModesParser {

	private static final Logger logger = LoggerFactory.getLogger(StatisticsSaveModesParser.class);

	public static final EnumSet<StatisticsSaveMode> parseSaveModes(String saveModesString) {
		EnumSet<StatisticsSaveMode> modes = EnumSet.noneOf(StatisticsSaveMode.class);

		if (saveModesString == null || saveModesString.length() == 0) {
			return modes;
		}

		String[] saveModeStringsList = saveModesString.split(",");
		for (String part : saveModeStringsList) {
			StatisticsSaveMode parsedMode = parseSaveMode(part);

			if (parsedMode == null) {
				logger.error("'{}' cannot be converted to StatisticsSaveMode. Whole save mode string: '{}'.", part,
						saveModesString);
			} else if (modes.contains(parsedMode)) {
				logger.error(
						"In save mode string there is '{}' specified multiple times. Whole save mode string: '{}'.",
						parsedMode, saveModesString);
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
