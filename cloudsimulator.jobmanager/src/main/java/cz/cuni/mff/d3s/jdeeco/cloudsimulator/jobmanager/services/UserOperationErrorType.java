package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.services;

/**
 * Type of error for invalid user operation.
 */
public enum UserOperationErrorType {
    
    NO_ERROR,
    
    /**
     * Trying to register already registered email.
     */
    EMAIL_ALREADY_REGISTERED,
    
    /**
     * Trying to login with not activated account.
     */
    ACCOUNT_NOT_ACTIVATED,
    
    /**
     * User does not exists.
     */
    NOT_FOUND,
    
    /**
     * Invalid value set in token expire creation.
     */
    TOKEN_EXPIRY_IS_INVALID
}
