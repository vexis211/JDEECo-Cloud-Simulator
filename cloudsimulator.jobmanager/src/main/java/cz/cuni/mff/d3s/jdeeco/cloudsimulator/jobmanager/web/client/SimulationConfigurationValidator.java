package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web.client;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.utils.StringHelper;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web.client.data.ProjectItem;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web.client.data.SimulationConfigurationItem;

/** Validator used for new project */
public class SimulationConfigurationValidator implements Validator {

	private static final String NAME_NOT_SPECIFIED_MESSAGE = "Name is not specified.";
	private static final String DESCRIPTION_NOT_SPECIFIED_MESSAGE = "Description is not specified.";

	@Override
	public boolean supports(Class<?> clazz) {
		return SimulationConfigurationItem.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		if (target == null) {
			errors.reject("error.empty", NAME_NOT_SPECIFIED_MESSAGE);
		} else {
			SimulationConfigurationItem item = (SimulationConfigurationItem) target;
			if (StringHelper.isNullOrEmpty(item.getName())) {
				errors.rejectValue(ProjectItem.NAME_FIELD, "error.empty", NAME_NOT_SPECIFIED_MESSAGE);
			} else if (StringHelper.isNullOrEmpty(item.getDescription())) {
				errors.rejectValue(ProjectItem.DESCRIPTION_FIELD, "error.empty", DESCRIPTION_NOT_SPECIFIED_MESSAGE);
			}
		}
	}
}
