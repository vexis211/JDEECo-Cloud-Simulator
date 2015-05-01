package cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web.client;

import org.springframework.validation.Errors;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web.client.data.SimulationDataItem;

/** Validator used for new project */
public class SimulationDataValidator extends BaseValidator<SimulationDataItem> {

	public static final String REPOSITORYURL_FIELD = "repositoryUrl";
	public static final String POMDIRECTORY_FIELD = "pomDirectory";
	public static final String MAVENGOALS_FIELD = "mavenGoals";
	public static final String STARTUPFILE_FIELD = "startupFile";

	@Override
	protected void validateInternal(SimulationDataItem item, Errors errors) {
		if (checkNotSpecified(item.getName())) {
			addNotSpecifiedError(errors, NAME_FIELD);
		} else if (checkNotSpecified(item.getDescription())) {
			addNotSpecifiedError(errors, DESCRIPTION_FIELD);
		} else if (checkNotSpecified(item.getRepositoryUrl())) {
			addNotSpecifiedError(errors, REPOSITORYURL_FIELD);
		} else if (checkNotSpecified(item.getPomDirectory())) {
			addNotSpecifiedError(errors, POMDIRECTORY_FIELD);
		} else if (checkNotSpecified(item.getMavenGoals())) {
			addNotSpecifiedError(errors, MAVENGOALS_FIELD);
		} else if (checkNotSpecified(item.getStartupFile())) {
			addNotSpecifiedError(errors, STARTUPFILE_FIELD);
		}
	}
}
