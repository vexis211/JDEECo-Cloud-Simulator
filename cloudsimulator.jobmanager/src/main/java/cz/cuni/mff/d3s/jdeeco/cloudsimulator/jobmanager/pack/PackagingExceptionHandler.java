package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.pack;

public interface PackagingExceptionHandler {
	void exceptionOccured(PackageTask packageTask, PackagingException e);
}
