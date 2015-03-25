package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.pack.processors;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.pack.PackageTask;

public interface PackageTaskProcessingListener {

	void packageTaskCompleted(PackageTask packageTask);
}
