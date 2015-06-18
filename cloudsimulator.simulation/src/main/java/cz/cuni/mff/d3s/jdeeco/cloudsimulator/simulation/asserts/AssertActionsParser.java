package cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.asserts;

import java.util.EnumSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.data.AssertAction;

public class AssertActionsParser {

	private static final Logger logger = LoggerFactory.getLogger(AssertActionsParser.class);

	public static final EnumSet<AssertAction> parseAssertActions(String assertActionsString) {
		EnumSet<AssertAction> actions = EnumSet.noneOf(AssertAction.class);

		if (assertActionsString == null || assertActionsString.length() == 0) {
			return actions;
		}

		String[] assertActionStringsList = assertActionsString.split(",");
		for (String part : assertActionStringsList) {
			AssertAction parsedAction = parseAssertAction(part);

			if (parsedAction == null) {
				logger.error("'{}' cannot be converted to AssertAction. Whole assert action string: '{}'.", part,
						assertActionsString);
			} else if (actions.contains(parsedAction)) {
				logger.error(
						"In assert action string there is '{}' specified multiple times. Whole assert action string: '{}'.",
						parsedAction, assertActionsString);
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
