package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.pack.processors;

import java.util.HashMap;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.pack.PackageTask;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.pack.PackagingException;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.pack.PackagingExceptionHandler;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.vcs.CodeRepositoryManager;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.FutureExecutor;

public class CodeRepositoryProcessor extends PackageTaskProcessorBase {

	protected final HashMap<String, PackageTask> unfinishedTasks = new HashMap<>();

	private final CodeRepositoryManager codeRepositoryManager;

	public CodeRepositoryProcessor(FutureExecutor executor, PackagingExceptionHandler exceptionHandler,
			CodeRepositoryManager codeRepositoryManager) {
		super(executor, exceptionHandler);

		this.codeRepositoryManager = codeRepositoryManager;
	}

	@Override
	protected boolean processInternal(PackageTask task) throws PackagingException {
		String remoteRepoUrl = task.getRepositoryRemoteUrl();

		// filter identical tasks
		synchronized (unfinishedTasks) {
			if (unfinishedTasks.containsKey(remoteRepoUrl)) {
				unfinishedTasks.get(remoteRepoUrl).getIdenticalTasksForProcessingStep().add(task);
				return false;
			} else {
				unfinishedTasks.put(remoteRepoUrl, task);
			}
		}

		try {
			processInternalCore(task);
		} finally {
			synchronized (unfinishedTasks) {
				unfinishedTasks.remove(remoteRepoUrl);
				// set to identical tasks
				task.getIdenticalTasksForProcessingStep().forEach(
						x -> x.setRepositoryLocalPath(task.getRepositoryLocalPath()));
			}
		}

		return true;
	}

	private void processInternalCore(PackageTask task) throws PackagingException {
		try {
			String localPath = codeRepositoryManager.prepareRepository(task.getRepositoryRemoteUrl(),
					task.getRepositoryType());
			task.setRepositoryLocalPath(localPath);
		} catch (Exception e) {
			throw new PackagingException(String.format(
					"Exception occured while getting updated repository. VCS system: '%s' Remote URL: '%s'.",
					task.getRepositoryType(), task.getRepositoryRemoteUrl()), e);
		}
	}
}
