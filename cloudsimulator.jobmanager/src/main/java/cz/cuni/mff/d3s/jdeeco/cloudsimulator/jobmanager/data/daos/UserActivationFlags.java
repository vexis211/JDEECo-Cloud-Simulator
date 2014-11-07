package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.daos;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.models.User;

/**
 * User activation flags
 * 
 * Determine user activation and registration state
 * 
 */
public class UserActivationFlags {
	/** This bit is set when the user is registered (user has registered with the service) */
	public static final int IS_REGISTERED = 1 << 1;
	/** This bit is set when the user is activated. Confirmed by activation email link. */
	public static final int IS_ACTIVATED = 1 << 2;

	/**
	 * Check whenever the user has specified flag set
	 * 
	 * @param user
	 *            user to check
	 * @param flags
	 *            Flags to check
	 * @return True when all flags specified are set.
	 */
	public static boolean hasFlags(User user, int flags) {
		return (user.getActivationState() & flags) == flags;
	}

	/**
	 * Add flags to user
	 * 
	 * @param user
	 *            User to modify
	 * @param flags
	 *            Flags to add
	 */
	public static void addFlags(User user, int flags) {
		user.setActivationState(user.getActivationState() | flags);
	}

	/**
	 * Remove flags from user
	 * 
	 * @param user
	 *            user to modify
	 * @param flags
	 *            Flags to remove
	 */
	public static void removeFlags(User user, int flags) {
		user.setActivationState(user.getActivationState() ^ flags);
	}
}
