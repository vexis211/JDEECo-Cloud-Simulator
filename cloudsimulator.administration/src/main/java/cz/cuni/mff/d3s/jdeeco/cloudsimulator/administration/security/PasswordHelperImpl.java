package cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.security;

import java.util.UUID;
import java.util.regex.Pattern;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.extensions.StringEx;


public class PasswordHelperImpl implements PasswordHelper {

	/** Required minimal password length */
	private final int PASSWORD_LENGTH = 8;
	
	@Override
	public String getRandomPassword() {
		char[] uuid = UUID.randomUUID().toString().toCharArray();
		StringBuilder passwordBuilder = new StringBuilder();
		for (int i = 0;i < uuid.length && passwordBuilder.length() < PASSWORD_LENGTH;++i) {
			if (uuid[i] != '-') {
				passwordBuilder.append(uuid[i]);
			}
		}
		return passwordBuilder.toString();
	}

	@Override
	public boolean isPasswordTooWeak(String password) {
		return StringEx.isNullOrEmpty(password) || password.length() < PASSWORD_LENGTH;
	}
	
	@Override
	public boolean hasPasswordIncorrectCharacters(String password) {
		return !Pattern.matches("^[a-zA-Z0-9]*$", password);
	}

}
