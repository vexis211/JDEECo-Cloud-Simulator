package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web.client;

/** Form for new password */
public class NewPasswordForm {

	public static final String PASSWORD_FIELD = "password";
	public static final String PASSWORD_VERIFY_FIELD = "passwordVerify";

	public static final String PASSWORD = "Password";
	public static final String PASS_CONFIRM = "Password confirmation";

	private String password;
	private String passwordVerify;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordVerify() {
		return passwordVerify;
	}

	public void setPasswordVerify(String passwordVerify) {
		this.passwordVerify = passwordVerify;
	}

	public String getPasswordPrompt() {
		return PASSWORD;
	}

	public String getPasswordVerifyPrompt() {
		return PASS_CONFIRM;
	}
}
