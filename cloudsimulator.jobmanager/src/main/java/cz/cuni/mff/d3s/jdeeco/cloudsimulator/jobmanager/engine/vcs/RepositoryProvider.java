package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.vcs;

public interface RepositoryProvider {
	
	VCSType getVCSType();
	
	void get(String remoteUrl, String localPath);
	void update(String localPath);
}
