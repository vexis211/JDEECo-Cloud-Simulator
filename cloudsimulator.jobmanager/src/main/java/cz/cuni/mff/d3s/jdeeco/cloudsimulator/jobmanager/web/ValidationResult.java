package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web;

import java.util.ArrayList;
import java.util.List;

/**
 * Validation error with multiple reasons.
 */
public class ValidationResult extends JsonResult {

	public static final String VALIDATION_FAILED = "VALIDATION_FAILED";

	/**
	 * List of errors happened during validation.
	 */
	private List<String> validationErrors;

	/**
	 * Constructor.
	 *
	 * @param result
	 *            Result of validation true/false.
	 * @param reason
	 *            Reason why validation failed, null on result == true.
	 */
	public ValidationResult(final boolean result, final String reason) {
		super(result, reason);
		validationErrors = new ArrayList<String>();
	}

	public void addValidationError(String errorCode) {
		validationErrors.add(errorCode);
	}

	public List<String> getValidationErrors() {
		return validationErrors;
	}

	public void setValidationErrors(List<String> validationErrors) {
		this.validationErrors = validationErrors;
	}

}
