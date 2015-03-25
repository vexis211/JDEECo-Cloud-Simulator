package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.planning;

public class PlanningException extends RuntimeException {

	private static final long serialVersionUID = 677170036635818094L;

	public PlanningException(String message, Throwable cause) {
		super(message, cause);
	}

	public PlanningException(String message) {
		super(message);
	}
}
