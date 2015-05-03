package cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web.client;

import javax.annotation.Resource;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.security.PasswordHelper;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.extensions.StringEx;

/** Validator used for new password */
public class NewPasswordValidator implements Validator {

	public static final String PASSWORD_NOT_MATCH_MESSAGE = "Password does not match with its verification!";
	public static final String PASSWORD_INVALID_CHARACTERS_MESSAGE = "Password has invalid characters. Only alpha-numberic characters are allowed.";
	public static final String PASSWORD_LENGTH_MESSAGE = "Password must have at least 8 characters!";
	public static final String EMAIL_NOT_SPECIFIED_MESSAGE = "Email is not specified.";

	@Resource
	private PasswordHelper passwordHelper;

	@Override
	public boolean supports(Class<?> clazz) {
		return NewPasswordForm.class.equals(clazz);
	}

	public boolean isEmpty(Object target) {
		if (target == null) {
			return true;
		}
		NewPasswordForm validatedObj = (NewPasswordForm) target;
		return StringEx.isNullOrEmpty(validatedObj.getPassword())
				|| StringEx.isNullOrEmpty(validatedObj.getPasswordVerify());
	}

	@Override
	public void validate(Object target, Errors errors) {
		NewPasswordForm validatedObj = (NewPasswordForm) target;
		if (validatedObj == null) {
			errors.rejectValue("email", "error.not-specified", EMAIL_NOT_SPECIFIED_MESSAGE);
		} else {
			if (StringEx.isNullOrEmpty(validatedObj.getPassword())) {
				errors.rejectValue(NewPasswordForm.PASSWORD_FIELD, "error.not-specified", PASSWORD_LENGTH_MESSAGE);
			} else if (passwordHelper.isPasswordTooWeak(validatedObj.getPassword())) {
				errors.rejectValue(NewPasswordForm.PASSWORD_FIELD, "error.too-weak", PASSWORD_LENGTH_MESSAGE);
			} else if (passwordHelper.hasPasswordIncorrectCharacters(validatedObj.getPassword())) {
				errors.rejectValue(NewPasswordForm.PASSWORD_FIELD, "errors.invalid-characters",
						PASSWORD_INVALID_CHARACTERS_MESSAGE);
			} else if (!validatedObj.getPassword().equals(validatedObj.getPasswordVerify())) {
				errors.rejectValue(NewPasswordForm.PASSWORD_VERIFY_FIELD, "errors.not-match",
						PASSWORD_NOT_MATCH_MESSAGE);
			}
		}
	}
}
