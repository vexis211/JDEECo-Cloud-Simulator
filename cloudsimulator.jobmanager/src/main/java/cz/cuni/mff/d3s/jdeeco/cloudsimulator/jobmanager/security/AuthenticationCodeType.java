package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.security;

/**
 * Supported code types for authentication.
 */
public enum AuthenticationCodeType {

    /**
     * Activation code. User can login when not activated yet but registered
     * already.
     */
    ACTIVATION,

    /**
     * Forgotten password code. User can login when activated and registered
     * already.
     */
    RESET_PASSWORD,

    /**
     * Anonymous login code. No specific requirements for this code.
     */
    ANONYMOUS_LOGIN,

    /**
     * Authentication token.
     */
    AUTH_TOKEN    
}
