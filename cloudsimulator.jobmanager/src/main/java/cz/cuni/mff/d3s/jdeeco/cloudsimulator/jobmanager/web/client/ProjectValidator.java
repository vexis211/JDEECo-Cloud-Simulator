package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web.client;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.utils.StringHelper;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web.client.data.ProjectItem;

/** Validator used for new project */
public class ProjectValidator implements Validator {

	private static final String NAME_NOT_SPECIFIED_MESSAGE = "Name is not specified.";
	private static final String DESCRIPTION_NOT_SPECIFIED_MESSAGE = "Description is not specified.";

	@Override
	public boolean supports(Class<?> clazz) {
		return ProjectItem.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		if (target == null) {
			errors.reject("error.empty", NAME_NOT_SPECIFIED_MESSAGE);
		} else {
			ProjectItem item = (ProjectItem) target;
			if (StringHelper.isNullOrEmpty(item.getName())) {
				errors.rejectValue(ProjectItem.NAME_FIELD, "error.empty", NAME_NOT_SPECIFIED_MESSAGE);
			} else if (StringHelper.isNullOrEmpty(item.getDescription())) {
				errors.rejectValue(ProjectItem.DESCRIPTION_FIELD, "error.empty", DESCRIPTION_NOT_SPECIFIED_MESSAGE);
			}
		}
	}
}
