package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.security;

/** Helper for checking password format */  
public interface PasswordHelper {
	/** get random password
	 * 
	 * @return Random password as string
	 */
	public String getRandomPassword();

	/**
	 * Check if the password is too weak
	 * 
	 * @param password Password as string
	 * @return True when too weak, false otherwise
	 */
	boolean isPasswordTooWeak(String password);
	
	/** Check if password has incorrect characters
	 * 
	 * @param password Password as string
	 * @return True when has incorrect characters, false otherwise
	 */
	boolean hasPasswordIncorrectCharacters(String password);
}
