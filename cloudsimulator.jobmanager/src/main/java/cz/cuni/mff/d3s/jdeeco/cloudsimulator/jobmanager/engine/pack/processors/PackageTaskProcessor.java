package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.pack.processors;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.pack.PackageTask;

public interface PackageTaskProcessor {
	/**
	 * Set processor which is called after process completed.
	 * @param nextProcessor next processor
	 * @return next processor
	 */
	PackageTaskProcessor continueProcess(PackageTaskProcessor nextProcessor);
	
	void process(PackageTask task);
}
