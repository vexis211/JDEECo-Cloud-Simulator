package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.pack;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.models.SimulationExecution;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.JobManagerUpdateQueue;

public class SimplePackageManagerImpl implements SimplePackageManager, PackageManagerListener {

	private final PackageManager packageManager;
	private final JobManagerUpdateQueue jobManagerUpdateQueue;

	public SimplePackageManagerImpl(PackageManager packageManager, JobManagerUpdateQueue jobManagerUpdateQueue) {
		this.packageManager = packageManager;
		this.jobManagerUpdateQueue = jobManagerUpdateQueue;
	}
	
	@Override
	public String getPackageName(SimulationExecution execution) {
		return packageManager.getPackageName(execution);
	}

	@Override
	public void preparePackage(SimulationExecution execution) {
		packageManager.preparePackage(execution, this);
	}

	@Override
	public void packagePrepared(int executionId, String packageName) {
		jobManagerUpdateQueue.add(new PackagePreparedUpdateImpl(executionId, packageName));
	}
}
