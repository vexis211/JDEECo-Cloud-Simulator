package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.pack;

import java.util.List;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.vcs.VCSType;

public interface PackageTask {
	int getId();
	
	List<PackageTask> getIdenticalTasksForProcessingStep();
	
	VCSType getRepositoryType();
	String getRepositoryRemoteUrl();
	String getRepositoryLocalPath();
	void setRepositoryLocalPath(String repositoryLocalPath);
	
	String getRelativePathToPomFile();
	List<String> getMavenGoals();
	String getCompileTargetDirectory();
	void setCompileTargetDirectory(String compileTargetDirectory);
	
	String getPreparingDirectory();
	void setPreparingDirectory(String preparingDirectory);

	String getPackageLocalPath();
	void setPackageLocalPath(String packageLocalPath);

	String getUploadName();
}