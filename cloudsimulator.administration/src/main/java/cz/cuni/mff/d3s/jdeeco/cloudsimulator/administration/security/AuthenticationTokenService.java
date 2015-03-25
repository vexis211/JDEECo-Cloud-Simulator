package cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.security;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.data.models.AuthenticationToken;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.services.UserOperationException;


/**
 * Service for manipulating authentication tokens.
 */
public interface AuthenticationTokenService {
    
    /**
     * Retrieves authentication token by value or null if not exists.
     * @param tokenValue Authentication token value.
     * @return AuthToken or null.
     */
	AuthenticationToken findByValue(String tokenValue);
    
    /**
     * Associates new token with user with expiry date.
     * 
     * @param userId
     *            Owner of token.
     * @param expireInSeconds
     *            Seconds before token is expired from now.
     * @return Authentication token.
     * @throws UserOperationException
     *             Thrown on error.
     */
	AuthenticationToken createToken(int userId, long expireInSeconds)
	    throws UserOperationException;

    /**
     * Associates new token with user. Expiry date will be set according to
     * project configuration.
     * 
     * @param userId
     *            Owner of token.
     * @return Authentication token.
     * @throws UserOperationException
     *             Thrown on error.
     */
	AuthenticationToken createDefaultToken(int userId) throws UserOperationException;
}
