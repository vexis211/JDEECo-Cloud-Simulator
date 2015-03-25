package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.pack.processors;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.pack.PackageTask;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.pack.PackagingException;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.pack.PackagingExceptionHandler;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.FutureExecutor;

public class NotifyPackageTaskCompletedProcessor extends PackageTaskProcessorBase {

	private final PackageTaskProcessingListener listener;

	public NotifyPackageTaskCompletedProcessor(FutureExecutor executor, PackagingExceptionHandler exceptionHandler,
			PackageTaskProcessingListener listener) {
		super(executor, exceptionHandler);
		this.listener = listener;
	}

	@Override
	protected boolean processInternal(PackageTask task) throws PackagingException {
		listener.packageTaskCompleted(task);
		return false;
	}
}
