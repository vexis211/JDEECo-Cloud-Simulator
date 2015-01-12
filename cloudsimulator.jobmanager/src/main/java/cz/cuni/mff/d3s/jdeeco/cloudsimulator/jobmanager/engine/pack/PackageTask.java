package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.pack;

import java.util.List;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.vcs.VCSType;

public interface PackageTask {
	List<PackageTask> getIdenticalTasksForProcessingStep();
	
	VCSType getRepositoryType();
	String getRepositoryRemoteUrl();
	String getRepositoryLocalPath();
	void setRepositoryLocalPath(String localPath);
	
	String getRelativePathToPomFile();
	List<String> getMavenGoals();
	
	String getPreparingDirectory();

	String getPackageLocalPath();
	String getUploadName();
}
