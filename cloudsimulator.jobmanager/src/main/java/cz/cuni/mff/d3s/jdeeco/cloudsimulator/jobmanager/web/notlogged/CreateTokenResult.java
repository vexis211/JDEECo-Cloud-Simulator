package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web.notlogged;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web.ValidationResult;

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
