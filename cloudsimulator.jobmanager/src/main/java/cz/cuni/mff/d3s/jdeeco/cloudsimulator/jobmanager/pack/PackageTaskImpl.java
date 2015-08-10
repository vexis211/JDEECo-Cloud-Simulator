package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.pack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.data.VCSType;

public class PackageTaskImpl implements PackageTask {

	private final int executionId;

	private List<PackageTask> identicalTasksForProcessingStep = new ArrayList<PackageTask>();

	private VCSType repositoryType;
	private String repositoryRemoteUrl;
	private String pomDirectory;
	private List<String> mavenGoals;
	private String compileTargetDirectory;

	private String repositoryLocalPath;

	private String preparingDirectory;

//	private String packageLocalPath;

	private String runProfile;
	private String statisticsProfile;
	private String assertsProfile;

	public PackageTaskImpl(int executionId) {
		this.executionId = executionId;
	}

	@Override
	public int getExecutionId() {
		return executionId;
	}

	@Override
	public List<PackageTask> getIdenticalTasksForProcessingStep() {
		return identicalTasksForProcessingStep;
	}

	@Override
	public VCSType getRepositoryType() {
		return repositoryType;
	}

	@Override
	public String getRepositoryRemoteUrl() {
		return repositoryRemoteUrl;
	}

	@Override
	public String getRepositoryLocalPath() {
		return repositoryLocalPath;
	}

	@Override
	public void setRepositoryLocalPath(String repositoryLocalPath) {
		this.repositoryLocalPath = repositoryLocalPath;
	}

	@Override
	public String getPomDirectoryName() {
		return pomDirectory;
	}

	@Override
	public List<String> getMavenGoals() {
		return mavenGoals;
	}

	@Override
	public String getCompileTargetDirectory() {
		return compileTargetDirectory;
	}

	@Override
	public void setCompileTargetDirectory(String compileTargetDirectory) {
		this.compileTargetDirectory = compileTargetDirectory;
	}

	@Override
	public String getPreparingDirectory() {
		return preparingDirectory;
	}

	@Override
	public void setPreparingDirectory(String preparingDirectory) {
		this.preparingDirectory = preparingDirectory;
	}

	@Override
	public String getRunProfile() {
		return runProfile;
	}

	@Override
	public String getStatisticsProfile() {
		return statisticsProfile;
	}

	@Override
	public String getAssertsProfile() {
		return assertsProfile;
	}

//	@Override
//	public String getPackageLocalPath() {
//		return packageLocalPath;
//	}
//
//	@Override
//	public void setPackageLocalPath(String packageLocalPath) {
//		this.packageLocalPath = packageLocalPath;
//	}

	// only in implementation
	void setRepositoryType(VCSType repositoryType) {
		this.repositoryType = repositoryType;
	}

	void setRepositoryRemoteUrl(String repositoryRemoteUrl) {
		this.repositoryRemoteUrl = repositoryRemoteUrl;
	}

	void setPomDirectory(String pomDirectory) {
		this.pomDirectory = pomDirectory;
	}

	void setMavenGoals(String[] mavenGoals) {
		this.mavenGoals = Collections.unmodifiableList(Arrays.asList(mavenGoals));
	}

	void setRunProfile(String runProfile) {
		this.runProfile = runProfile;
	}

	void setStatisticsProfile(String statisticsProfile) {
		this.statisticsProfile = statisticsProfile;
	}

	void setAssertsProfile(String assertsProfile) {
		this.assertsProfile = assertsProfile;
	}
}
