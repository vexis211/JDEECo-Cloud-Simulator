package cz.cuni.mff.d3s.jdeeco.cloudsimulator.worker.engine;

public class UnknownWorkerTaskException extends RuntimeException {

	private static final long serialVersionUID = -5775775111368059278L;

	public UnknownWorkerTaskException(String message) {
		super(message);
	}
}
