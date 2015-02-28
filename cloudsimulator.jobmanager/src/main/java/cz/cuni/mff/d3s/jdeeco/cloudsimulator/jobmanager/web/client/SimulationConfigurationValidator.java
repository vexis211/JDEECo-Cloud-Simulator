package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web.client;

import org.springframework.validation.Errors;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web.client.data.SimulationConfigurationItem;

/** Validator used for new project */
public class SimulationConfigurationValidator extends BaseValidator<SimulationConfigurationItem> {

	public static final String DEFAULTRUNCOUNT_FIELD = "defaultRunCount";
	
	
	@Override
	protected void validateInternal(SimulationConfigurationItem item, Errors errors) {
		if (checkNotSpecified(item.getName())) {
			addNotSpecifiedError(errors, NAME_FIELD);
		} else if (checkNotSpecified(item.getDescription())) {
			addNotSpecifiedError(errors, DESCRIPTION_FIELD);
		} else if (item.getDefaultRunCount() < 1) {
			addInvalidValueError(errors, DEFAULTRUNCOUNT_FIELD, "Default run count", "at least 1");
		}
	}
}
