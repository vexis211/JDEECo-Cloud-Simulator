package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.workers;

public interface WorkerIdGenerator {
	public static final String ID_PREFIX = "worker_";
	
	String generate();
}
