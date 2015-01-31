package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.pack;

public interface PackageManagerListener {
	void packagePrepared(int executionId, String packageName);
}
