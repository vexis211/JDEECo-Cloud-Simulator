package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.pack;

import java.util.List;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.data.VCSType;

public interface PackageTask {
	int getExecutionId();

	/**
	 * In some phases more package task can be processed only once, in that case processed task remembers skipped tasks
	 * for being processed by next processors
	 * 
	 * @return skipped tasks for current processing step
	 */
	List<PackageTask> getIdenticalTasksForProcessingStep();

	VCSType getRepositoryType();
	String getRepositoryRemoteUrl();
	String getRepositoryLocalPath();
	void setRepositoryLocalPath(String repositoryLocalPath);

	String getPomDirectoryName();
	List<String> getMavenGoals();

	String getCompileTargetDirectory();
	void setCompileTargetDirectory(String compileTargetDirectory);

	String getPreparingDirectory();
	void setPreparingDirectory(String preparingDirectory);

	String getRunProfile();
	String getStatisticsProfile();
	String getAssertsProfile();
	
//	String getPackageLocalPath();
//	void setPackageLocalPath(String packageLocalPath);
}
