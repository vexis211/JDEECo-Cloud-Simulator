package cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.services;

/**
 * Invalid operation with user's account.
 */
public class UserOperationException extends Exception {

	/**
	 * Parameter of Serializable.
	 */
	private static final long serialVersionUID = 6335358935466088170L;

	/**
	 * Error that is cause of exception.
	 */
	private final UserOperationErrorType errorType;

	/**
	 * Initialize with error type.
	 * 
	 * @param errorType
	 *            Type of error causing exception.
	 */
	public UserOperationException(final UserOperationErrorType errorType) {
		this.errorType = errorType;
	}

	@Override
	public final String toString() {
		return String.format("%s due to reason %s", super.toString(), errorType.name());
	}

	public final UserOperationErrorType getErrorType() {
		return errorType;
	}
}
