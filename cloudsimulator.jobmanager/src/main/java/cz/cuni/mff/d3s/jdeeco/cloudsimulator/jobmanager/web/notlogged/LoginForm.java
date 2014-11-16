package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web.notlogged;

import org.apache.log4j.Logger;

/** Login form */
public class LoginForm {

	public static final String EMAIL_FIELD = "email";
	public static final String PASSWORD_FIELD = "password";

	protected final Logger logger = Logger.getLogger(LoginController.class);

	private String email;
	private String password;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
