package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.pack;

public interface PackagePreparatorListener {
	void packagePrepared(PackageTask packageTask);
	void packageExceptionOccured(PackageTask packageTask, PackagingException e);	
}
