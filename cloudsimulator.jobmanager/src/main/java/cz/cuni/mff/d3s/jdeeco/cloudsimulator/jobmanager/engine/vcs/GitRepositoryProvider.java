package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.vcs;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;

public class GitRepositoryProvider implements RepositoryProvider {

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
	        System.out.println("Having repository: " + git.getRepository().getDirectory());
		} catch (GitAPIException e) {
			System.out.println("Error while getting repository: " + remoteUrl + " to: " + localPath);
			e.printStackTrace();
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
		} catch (GitAPIException | IOException e) {
			System.out.println("Error while updating repository: " + localPath);
			e.printStackTrace();
		} finally {
			if (git != null) git.close();
		}		
	}
}
