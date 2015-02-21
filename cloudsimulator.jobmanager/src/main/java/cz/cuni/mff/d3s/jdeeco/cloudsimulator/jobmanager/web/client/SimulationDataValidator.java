package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web.client;

import org.springframework.validation.Errors;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web.client.data.SimulationDataItem;

/** Validator used for new project */
public class SimulationDataValidator extends BaseValidator<SimulationDataItem> {

	public static final String REPOSITORYURL_FIELD = "repositoryUrl";
	public static final String PATHTOPOM_FIELD = "pathToPom";
	public static final String MAVENGOALS_FIELD = "mavenGoals";

	@Override
	protected void validateInternal(SimulationDataItem item, Errors errors) {
		if (checkNotSpecified(item.getName())) {
			addNotSpecifiedError(errors, NAME_FIELD);
		} else if (checkNotSpecified(item.getDescription())) {
			addNotSpecifiedError(errors, DESCRIPTION_FIELD);
		} else if (checkNotSpecified(item.getRepositoryUrl())) {
			addNotSpecifiedError(errors, REPOSITORYURL_FIELD);
		} else if (checkNotSpecified(item.getPathToPom())) {
			addNotSpecifiedError(errors, PATHTOPOM_FIELD);
		} else if (checkNotSpecified(item.getMavenGoals())) {
			addNotSpecifiedError(errors, MAVENGOALS_FIELD);
		}
	}
}
