package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.pack;

public interface PackagePreparator {
	void preparePackage(PackageTask packageTask, PackagePreparatorListener listener);	
}
