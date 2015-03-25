package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.vcs;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.data.VCSType;

public interface RepositoryProvider {
	
	VCSType getVCSType();
	
	void get(String remoteUrl, String localPath);
	void update(String localPath);
}
