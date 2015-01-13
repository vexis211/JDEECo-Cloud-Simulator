package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.pack.processors;

import java.util.List;
import java.util.stream.Collectors;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.pack.PackageTask;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.pack.PackagingException;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.pack.PackagingExceptionHandler;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.FutureExecutor;

public abstract class PackageTaskProcessorBase implements PackageTaskProcessor {

	private final FutureExecutor executor;
	private final PackagingExceptionHandler exceptionHandler;

	private PackageTaskProcessor nextProcessor;

	public PackageTaskProcessorBase(FutureExecutor executor, PackagingExceptionHandler exceptionHandler) {
		this.executor = executor;
		this.exceptionHandler = exceptionHandler;
	}

	@Override
	public PackageTaskProcessor continueProcess(PackageTaskProcessor nextProcessor) {
		this.nextProcessor = nextProcessor;
		return nextProcessor;
	};

	@Override
	public void process(PackageTask task) {
		Runnable runnable = () -> processAndContinue(task);
		executor.execute(runnable);
	}

	private void processAndContinue(PackageTask task) {
		boolean shouldContinue;
		try {
			shouldContinue = processInternal(task);

			if (shouldContinue && nextProcessor != null) {
				List<PackageTask> identicalTasks = task.getIdenticalTasksForProcessingStep().stream()
						.collect(Collectors.toList());
				task.getIdenticalTasksForProcessingStep().clear();

				nextProcessor.process(task);
				identicalTasks.forEach(x -> nextProcessor.process(x));
			}
		} catch (PackagingException e) {
			e.printStackTrace();

			List<PackageTask> identicalTasks = task.getIdenticalTasksForProcessingStep().stream()
					.collect(Collectors.toList());
			task.getIdenticalTasksForProcessingStep().clear();

			exceptionHandler.exceptionOccured(task, e);
			identicalTasks.forEach(x -> exceptionHandler.exceptionOccured(x, e));			
		}
	}

	protected abstract boolean processInternal(PackageTask task) throws PackagingException;
}
