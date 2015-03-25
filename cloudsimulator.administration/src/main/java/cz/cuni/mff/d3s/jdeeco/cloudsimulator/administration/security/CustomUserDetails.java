package cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.security;

import org.springframework.security.core.userdetails.UserDetails;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.models.User;


/**
 * Extended spring security UserDetails to contain underlying User model.
 */
public interface CustomUserDetails extends UserDetails {
    
    /**
     * Gets underlying user model, which is source of other details
     * from UserDetails.
     * @return User model.
     */
    User getUser();
}
