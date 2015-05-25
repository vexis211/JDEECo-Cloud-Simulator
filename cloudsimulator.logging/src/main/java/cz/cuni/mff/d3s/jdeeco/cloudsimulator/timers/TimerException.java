package cz.cuni.mff.d3s.jdeeco.cloudsimulator.timers;

/**
 * Thrown when an timer exception occurs.
 */
public class TimerException extends RuntimeException {

	private static final long serialVersionUID = -7324478148669523540L;

	public TimerException() {
		super();
	}

	public TimerException(String message, Throwable cause) {
		super(message, cause);
	}

	public TimerException(String message) {
		super(message);
	}

	public TimerException(Throwable cause) {
		super(cause);
	}
}