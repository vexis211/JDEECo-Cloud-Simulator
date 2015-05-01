package cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web.client.data;

import java.util.Date;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.data.VCSType;

public interface SimulationDataItem {

	int getId();
	void setId(int id);
	
	Date getCreated();
	String getCreator();

	String getName();
	void setName(String name);

	String getDescription();
	void setDescription(String description);

	VCSType getVcsType();
	void setVcsType(VCSType vcsType);
	
	String getRepositoryUrl();
	void setRepositoryUrl(String repositoryUrl);
	
	String getPomDirectory();
	void setPomDirectory(String pomDirectory);
	
	String getMavenGoals();
	void setMavenGoals(String mavenGoals);
	
	String getStartupFile();
	void setStartupFile(String startupFile);
}
