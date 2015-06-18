package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.vcs;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.data.VCSType;

public class GitRepositoryProvider implements RepositoryProvider {

	private final Logger logger = LoggerFactory.getLogger(GitRepositoryProvider.class);
	
	@Override
	public VCSType getVCSType() {
		return VCSType.Git;
	}

	@Override
	public void get(String remoteUrl, String localPath) {
		File localDir = new File(localPath);
		Git git = null;
		try {
			git = Git.cloneRepository().setURI(remoteUrl).setDirectory(localDir).call();
			logger.info("Cloning repository: {} to {}.", remoteUrl, localPath);
		} catch (GitAPIException e) {
			throw new RuntimeException(String.format("Error while getting repository: %s to: %s.", remoteUrl, localPath));
		} finally {
			if (git != null) git.close();
		}
	}

	@Override
	public void update(String localPath) {
		Git git = null;
		try {
			git = Git.open(new File(localPath));
			git.pull().call();
			logger.info("Updating repository: {}.", localPath);
		} catch (GitAPIException | IOException e) {
			throw new RuntimeException(String.format("Error while updating repository: %s.", localPath));
		} finally {
			if (git != null) git.close();
		}		
	}
}
