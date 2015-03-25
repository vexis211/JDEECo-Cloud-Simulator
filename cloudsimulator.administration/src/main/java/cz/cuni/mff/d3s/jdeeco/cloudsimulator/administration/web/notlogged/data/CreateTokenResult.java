package cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web.notlogged.data;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web.ValidationResult;

public class CreateTokenResult extends ValidationResult {

	private String token;

	public CreateTokenResult(boolean result, String reason, String token) {
		super(result, reason);
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
