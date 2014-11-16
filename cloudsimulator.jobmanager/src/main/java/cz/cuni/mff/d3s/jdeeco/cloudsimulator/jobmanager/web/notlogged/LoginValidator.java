package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web.notlogged;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.models.User;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.services.UserService;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.utils.ValidatorHelper;

/**
 * Validates login form.
 */
public class LoginValidator implements Validator {

	/**
	 * Possible errors in validation of login form.
	 */
	public static enum ErrorType {
		/**
		 * Email has illegal format.
		 */
		EMAIL_INVALID_VALUE,
		/**
		 * Email is not in database.
		 */
		EMAIL_NOT_FOUND,
		/**
		 * Password is not set.
		 */
		PASSWORD_EMPTY
	}

	private static final String VALUE_REQUIRED = "Value required.";
	private static final String EMAIL_BAD_FORMAT = "Bad format.";
	private static final String EMAIL_NOT_EXISTS = "Email not exists.";
	
	protected final Logger logger = Logger.getLogger(LoginController.class);

	@Resource
	private UserService userService;

	private String login = null;
	private String password = null;

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return LoginForm.class.equals(clazz);
	}

	@Override
	public void validate(final Object target, final Errors errors) {
		LoginForm credentials = (LoginForm) target;
		if (credentials == null) {
			// Arguments not set.
			errors.rejectValue(LoginForm.EMAIL_FIELD, ErrorType.EMAIL_INVALID_VALUE.name(), VALUE_REQUIRED);
			errors.rejectValue(LoginForm.PASSWORD_FIELD, ErrorType.PASSWORD_EMPTY.name(), VALUE_REQUIRED);
		} else {
			if (credentials.getEmail() == null) {
				// Email not in arguments.
				errors.rejectValue(LoginForm.EMAIL_FIELD, ErrorType.EMAIL_INVALID_VALUE.name(), VALUE_REQUIRED);
			} else if (ValidatorHelper.isNotEmail(credentials.getEmail())) {
				// Email in arguments but with illegal format.
				errors.rejectValue(LoginForm.EMAIL_FIELD, ErrorType.EMAIL_INVALID_VALUE.name(), EMAIL_BAD_FORMAT);
			} else {
				// Email is passed correctly, check if it exists in DB.
				User user = userService.findByEmail(credentials.getEmail());
				if (user == null) {
					errors.rejectValue(LoginForm.EMAIL_FIELD, ErrorType.EMAIL_NOT_FOUND.name(), EMAIL_NOT_EXISTS);
				}
			}
			if (credentials.getPassword() == null) {
				// Password not in arguments.
				errors.rejectValue(LoginForm.PASSWORD_FIELD, ErrorType.PASSWORD_EMPTY.name(), VALUE_REQUIRED);
			}
		}
	}
}
