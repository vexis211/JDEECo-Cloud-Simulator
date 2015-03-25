package cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.security;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

/**
 * Authentication token for authenticating with various code types.
 * 
 * @see AuthenticationCodeType
 */
public class CodeAuthenticationToken extends AbstractAuthenticationToken {

	/**
	 * Parameter of Serializable.
	 */
	private static final long serialVersionUID = -3488482418899351542L;

	/**
	 * Authetincated object represented by this token instance.
	 */
	private Object principal;

	/**
	 * Authentication code type.
	 * 
	 * @see AuthenticationCodeType
	 */
	private AuthenticationCodeType codeType;

	/**
	 * Authentication code.
	 */
	private String code;

	/**
	 * Constructs already authenticated token.
	 * 
	 * @param codeType
	 *            Authentication code type.
	 * @param code
	 *            Authentication code.
	 * @param principal
	 *            Authenticated object represented by this token.
	 * @param authorities
	 *            Authenticated object authorities.
	 */
	public CodeAuthenticationToken(final AuthenticationCodeType codeType, final String code, final Object principal,
			final Collection<? extends GrantedAuthority> authorities) {
		super(authorities);
		this.codeType = codeType;
		this.code = code;
		this.principal = principal;
	}

	/**
	 * Constructs not authenticated token.
	 * 
	 * @param codeType
	 *            Authentication code type.
	 * @param code
	 *            Authentication token.
	 */
	public CodeAuthenticationToken(final AuthenticationCodeType codeType, final String code) {
		super(null);
		this.codeType = codeType;
		this.code = code;
		this.principal = null;
	}

	/**
	 * Always null. Credentials are supported with code.
	 * 
	 * @return null
	 */
	@Override
	public final Object getCredentials() {
		return null;
	}

	/**
	 * Retrieves authenticated object.
	 * 
	 * @return Authenticated object.
	 */
	@Override
	public final Object getPrincipal() {
		return principal;
	}

	/**
	 * Retrieves authentication code type.
	 * 
	 * @return Authentication code type.
	 */
	public final AuthenticationCodeType getCodeType() {
		return codeType;
	}

	/**
	 * Retrieves authentication code.
	 * 
	 * @return Authentication code.
	 */
	public final String getCode() {
		return code;
	}
}
