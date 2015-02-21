package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web.client.data;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.vcs.VCSType;

public interface SimulationDataItem {

	int getId();

	String getName();
	void setName(String name);

	String getDescription();
	void setDescription(String description);

	VCSType getVcsType();
	void setVcsType(VCSType vcsType);
	
	String getRepositoryUrl();
	void setRepositoryUrl(String repositoryUrl);
	
	String getPathToPom();
	void setPathToPom(String pathToPom);
	
	String getMavenGoals();
	void setMavenGoals(String mavenGoals);
}
