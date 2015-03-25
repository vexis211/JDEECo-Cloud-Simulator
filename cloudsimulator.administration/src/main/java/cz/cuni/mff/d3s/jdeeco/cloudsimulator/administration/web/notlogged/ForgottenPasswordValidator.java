package cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web.notlogged;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.utils.ValidatorHelper;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web.notlogged.data.ForgottenPasswordForm;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.StringEx;

/** Validator used for forgotten password */
public class ForgottenPasswordValidator implements Validator {

	private static final String EMAIL_NOT_SPECIFIED_MESSAGE = "Email is not specified.";
	private static final String EMAIL_INVALID_FORMAT = "Email is not in correct format: name@domain.tld";

	@Override
	public boolean supports(Class<?> clazz) {
		return ForgottenPasswordForm.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		if (target == null) {
			errors.reject("error.empty", EMAIL_NOT_SPECIFIED_MESSAGE);
		} else {
			ForgottenPasswordForm form = (ForgottenPasswordForm) target;
			if (StringEx.isNullOrEmpty(form.getEmail())) {
				errors.rejectValue(ForgottenPasswordForm.EMAIL_FIELD, "error.empty", EMAIL_NOT_SPECIFIED_MESSAGE);
			} else if (ValidatorHelper.isNotEmail(form.getEmail())) {
				errors.rejectValue(ForgottenPasswordForm.EMAIL_FIELD, "error.invalid-format", EMAIL_INVALID_FORMAT);
			}
		}
	}

}
