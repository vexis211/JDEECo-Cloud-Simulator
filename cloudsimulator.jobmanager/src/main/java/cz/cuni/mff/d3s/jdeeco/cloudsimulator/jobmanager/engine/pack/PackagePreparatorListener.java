package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.pack;

public interface PackagePreparatorListener {
	void packagePrepared(PackageTask packageTask);
	void packageExceptionOccured(PackageTask packageTask, PackagingException e);	
}
