package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.statistics.parsers;

public class ParseException extends RuntimeException {

	private static final long serialVersionUID = -9168912497639660060L;

	public ParseException(String message) {
		super(message);
	}

	public ParseException(String message, Throwable cause) {
		super(message, cause);
	}
}
