package cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web.client;

import org.springframework.validation.Errors;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web.client.data.ProjectItem;

/** Validator used for new project */
public class ProjectValidator extends BaseValidator<ProjectItem> {

	@Override
	protected void validateInternal(ProjectItem item, Errors errors) {
		if (checkNotSpecified(item.getName())) {
			addNotSpecifiedError(errors, NAME_FIELD);
		} else if (checkNotSpecified(item.getDescription())) {
			addNotSpecifiedError(errors, DESCRIPTION_FIELD);
		}
	}
}
