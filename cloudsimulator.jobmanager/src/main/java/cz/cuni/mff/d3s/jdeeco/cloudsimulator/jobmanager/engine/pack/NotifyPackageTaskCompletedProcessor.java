package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.pack;

import java.util.HashMap;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.FutureExecutor;

public class NotifyPackageTaskCompletedProcessor extends PackageTaskProcessorBase {

	protected final HashMap<String, PackageTask> unfinishedTasks = new HashMap<>();
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
