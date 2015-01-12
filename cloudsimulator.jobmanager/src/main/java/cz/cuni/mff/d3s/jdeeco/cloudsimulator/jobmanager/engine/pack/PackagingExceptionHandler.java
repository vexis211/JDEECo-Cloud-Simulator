package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.pack;

public interface PackagingExceptionHandler {
	void exceptionOccured(PackageTask task, PackagingException e);
}
