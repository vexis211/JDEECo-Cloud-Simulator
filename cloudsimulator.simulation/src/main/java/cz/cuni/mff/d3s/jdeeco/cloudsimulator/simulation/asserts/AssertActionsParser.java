package cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.asserts;

import java.util.EnumSet;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.data.AssertAction;

public class AssertActionsParser {

	private static final Logger logger = LogManager.getLogger(AssertActionsParser.class);

	public static final EnumSet<AssertAction> parseAssertActions(String assertActionsString) {
		EnumSet<AssertAction> actions = EnumSet.noneOf(AssertAction.class);

		if (assertActionsString == null || assertActionsString.length() == 0) {
			return actions;
		}

		String[] assertActionStringsList = assertActionsString.split(",");
		for (String part : assertActionStringsList) {
			AssertAction parsedAction = parseAssertAction(part);

			if (parsedAction == null) {
				logger.error(String.format(
						"'%s' cannot be converted to AssertAction. Whole assert action string: '%s'.", part,
						assertActionsString));
			} else if (actions.contains(parsedAction)) {
				logger.error(String.format(
						"In assert action string there is '%s' specified multiple times. Whole assert action string: '%s'.",
						parsedAction, assertActionsString));
			} else {
				actions.add(parsedAction);
			}
		}

		return actions;
	}

	public static final AssertAction parseAssertAction(String assertAction) {
		for (AssertAction action : EnumSet.allOf(AssertAction.class)) {
			if (action.name().equalsIgnoreCase(assertAction)) {
				return action;
			}
		}

		return null;
	}

}
