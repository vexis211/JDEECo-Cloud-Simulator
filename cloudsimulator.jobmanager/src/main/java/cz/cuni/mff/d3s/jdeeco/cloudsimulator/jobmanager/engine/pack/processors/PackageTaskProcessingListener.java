package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.pack.processors;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.pack.PackageTask;

public interface PackageTaskProcessingListener {

	void packageTaskCompleted(PackageTask packageTask);
}
