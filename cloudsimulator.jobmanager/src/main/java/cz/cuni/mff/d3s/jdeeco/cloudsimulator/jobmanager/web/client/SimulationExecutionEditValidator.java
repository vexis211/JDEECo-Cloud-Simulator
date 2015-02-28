package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web.client;

import org.springframework.validation.Errors;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web.client.data.SimulationExecutionItem;

/** Validator used for new project */
public class SimulationExecutionEditValidator extends BaseValidator<SimulationExecutionItem> {

	@Override
	protected void validateInternal(SimulationExecutionItem item, Errors errors) {
		if (checkNotSpecified(item.getDescription())) {
			addNotSpecifiedError(errors, DESCRIPTION_FIELD);
		}
	}
}
