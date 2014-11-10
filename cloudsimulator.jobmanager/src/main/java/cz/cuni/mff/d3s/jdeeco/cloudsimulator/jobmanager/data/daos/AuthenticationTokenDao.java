package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.daos;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.models.AuthenticationToken;

/**
 * Storage interface for manipulating with authentication tokens.
 */
public interface AuthenticationTokenDao extends BaseDao<AuthenticationToken> {

    /**
     * Retrieves token by its value or null if not found.
     * @param value Value to find.
     * @return Token or null.
     */
    AuthenticationToken findByValue(String value);
}
