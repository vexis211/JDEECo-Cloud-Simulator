package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.pack;

public interface PackagePreparator {
	void preparePackage(PackageTask packageTask, PackagePreparatorListener listener);	
}
