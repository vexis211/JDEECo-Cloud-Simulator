package cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.security;

import org.springframework.security.core.AuthenticationException;

/**
 * Thrown when there is no appropriate code type authenticator found.
 * 
 * @see CodeAuthenticationProvider
 * @see CodeAuthenticator
 */
public class CodeTypeNotSupportedException extends AuthenticationException {

	/**
	 * Parameter of Serializable.
	 */
	private static final long serialVersionUID = -2452316020830766451L;

	/**
	 * Constructor.
	 * 
	 * @param codeType
	 *            Code type causing error.
	 */
	public CodeTypeNotSupportedException(final AuthenticationCodeType codeType) {
		super(String.format("Code type is not supported '%s'", codeType.name()));
	}

}
