package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.pack.processors;

import java.io.File;
import java.util.HashMap;

import org.apache.maven.shared.invoker.DefaultInvocationRequest;
import org.apache.maven.shared.invoker.DefaultInvoker;
import org.apache.maven.shared.invoker.InvocationRequest;
import org.apache.maven.shared.invoker.InvocationResult;
import org.apache.maven.shared.invoker.Invoker;
import org.apache.maven.shared.invoker.MavenInvocationException;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.extensions.PathEx;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.pack.PackageTask;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.pack.PackagingException;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.pack.PackagingExceptionHandler;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.FutureExecutor;

public class CompileCodeProcessor extends PackageTaskProcessorBase {

	protected final HashMap<String, PackageTask> unfinishedTasks = new HashMap<>();

	public CompileCodeProcessor(FutureExecutor executor, PackagingExceptionHandler exceptionHandler) {
		super(executor, exceptionHandler);
	}

	@Override
	protected boolean processInternal(PackageTask task) throws PackagingException {
		String localRepoPath = task.getRepositoryLocalPath();

		// filter identical tasks
		synchronized (unfinishedTasks) {
			if (unfinishedTasks.containsKey(localRepoPath)) {
				unfinishedTasks.get(localRepoPath).getIdenticalTasksForProcessingStep().add(task);
				return false;
			} else {
				unfinishedTasks.put(localRepoPath, task);
			}
		}

		try {
			processInternalCore(task);
		} finally {
			synchronized (unfinishedTasks) {
				unfinishedTasks.remove(localRepoPath);
				// set to identical tasks
				task.getIdenticalTasksForProcessingStep().forEach(
						x -> x.setCompileTargetDirectory(task.getCompileTargetDirectory()));
			}
		}

		return true;
	}

	private void processInternalCore(PackageTask task) throws PackagingException {
		// TODO improvement - make universal - not only maven
		String pathToPom = PathEx.combine(task.getRepositoryLocalPath(), task.getRelativePathToPomFile());

		InvocationRequest request = new DefaultInvocationRequest();
		request.setInteractive(false);
		request.setPomFile(new File(pathToPom));
		request.setGoals(task.getMavenGoals());

		Invoker invoker = new DefaultInvoker();
		InvocationResult result;
		try {
			result = invoker.execute(request);
			task.setCompileTargetDirectory("target"); // TODO improvement - other build managers?
		} catch (MavenInvocationException e) {
			e.printStackTrace();
			throw new PackagingException(String.format(
					"Error occured while invoking maven. POM file path: '%s' Goals: '%s'.", pathToPom,
					String.join(", ", task.getMavenGoals())), e);
		}

		if (result.getExitCode() != 0) {
			if (result.getExecutionException() != null) {
				throw new PackagingException(String.format(
						"Failed to compile using maven. POM file path: '%s' Goals: '%s'.", pathToPom,
						String.join(", ", task.getMavenGoals())), result.getExecutionException());
			} else {
				throw new PackagingException(String.format(
						"Failed to compile using maven. Exit code: '%s'. POM file path: '%s' Goals: '%s'.",
						result.getExitCode(), pathToPom, String.join(", ", task.getMavenGoals())));
			}
		}
	}
}
