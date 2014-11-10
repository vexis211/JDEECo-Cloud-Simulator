package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.security;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.models.User;

/**
 * Provides user retrieval services for CodeAuthenticators.
 */
public interface SecurityService {
    /**
     * Retrieves user with provided anonymous login code.
     * 
     * @param anonymousLoginCode
     *            Code identifying one user.
     * @return User or null.
     */
    User findByAnonymousLoginCode(String anonymousLoginCode);

    /**
     * Retrieves user with provided reset password code.
     * 
     * @param resetPasswordCode
     *            Reset code identifying one user.
     * @return User or null.
     */
    User findByResetCode(String resetPasswordCode);

    /**
     * Retrieves user with provided activation code.
     * 
     * @param activationCode
     *            Activation code identifying one user.
     * @return User or null.
     */
    User findByActivationCode(String activationCode);
}
