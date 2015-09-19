package cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web.client;

import org.springframework.validation.Errors;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web.client.data.SimulationConfigurationItem;

/** Validator used for new project */
public class SimulationConfigurationValidator extends BaseValidator<SimulationConfigurationItem> {

	public static final String DEFAULTRUNMULTIPLICATOR_FIELD = "defaultRunMultiplicator";
	
	
	@Override
	protected void validateInternal(SimulationConfigurationItem item, Errors errors) {
		if (checkNotSpecified(item.getName())) {
			addNotSpecifiedError(errors, NAME_FIELD);
		} 
		if (checkNotSpecified(item.getDescription())) {
			addNotSpecifiedError(errors, DESCRIPTION_FIELD);
		} 
		if (item.getDefaultRunMultiplicator() < 1) {
			addInvalidValueError(errors, DEFAULTRUNMULTIPLICATOR_FIELD, "Default run multiplicator", "at least 1");
		}
	}
}
