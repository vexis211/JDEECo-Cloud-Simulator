package cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web.client;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.data.ExecutionEndSpecificationType;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web.client.data.SimulationExecutionItem;

/** Validator used for new project */
public class SimulationExecutionValidator extends BaseValidator<SimulationExecutionItem> {

	public static final String RUNCOUNT_FIELD = "runCount";
	public static final String ENDSPECTYPE_FIELD = "endSpecificationType";
	public static final String ENDDATE_FIELD = "endDate";
	public static final String RUNPROFILE_FIELD = "runProfile";
	public static final String STATISTICSPROFILE_FIELD = "statisticsProfile";
	public static final String ASSERTSPROFILE_FIELD = "assertsProfile";

	@Override
	protected void validateInternal(SimulationExecutionItem item, Errors errors) {
		if (checkNotSpecified(item.getDescription())) {
			addNotSpecifiedError(errors, DESCRIPTION_FIELD);
		}
		if (item.getRunCount() != null && item.getRunCount() < 1) {
			addInvalidValueError(errors, RUNCOUNT_FIELD, "Specific run count", "at least 1");
		}
		if (item.getEndSpecificationType() == null) {
			addNotSpecifiedError(errors, ENDSPECTYPE_FIELD, "End type");
		} else if (item.getEndSpecificationType() == ExecutionEndSpecificationType.ToDate) {
			if (StringUtils.isEmpty(item.getEndDateString())) {
				addNotSpecifiedError(errors, ENDDATE_FIELD, "End date");
			} else if (item.getEndDate() == null) {
				addInvalidValueError(errors, ENDDATE_FIELD, "End date", "for example: 26/02/2015 21:23:29");
			}
		}
		if (checkNotSpecified(item.getRunProfile())) {
			addNotSpecifiedError(errors, RUNPROFILE_FIELD);
		}
		if (checkNotSpecified(item.getStatisticsProfile())) {
			addNotSpecifiedError(errors, STATISTICSPROFILE_FIELD);
		}
		if (checkNotSpecified(item.getAssertsProfile())) {
			addNotSpecifiedError(errors, ASSERTSPROFILE_FIELD);
		}
	}
}
