package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.pack;

public class PackagingException extends Exception {

	private static final long serialVersionUID = 677170036635818094L;

	public PackagingException(String message, Throwable cause) {
		super(message, cause);
	}

	public PackagingException(String message) {
		super(message);
	}
}
