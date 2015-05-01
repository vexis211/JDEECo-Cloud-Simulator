package cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web.client.data;

import java.util.Date;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.models.SimulationData;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.data.VCSType;

public class SimulationDataItemImpl implements SimulationDataItem {
	private int id;
	private Date created;
	private String creator;
	
	private String name;
	private String description;
	private VCSType vcsType;
	private String repositoryUrl;
	private String pomDirectory;
	private String mavenGoals;
	private String startupFile;

	public SimulationDataItemImpl() {
	}

	public SimulationDataItemImpl(SimulationData data) {
		this.id = data.getId();
		this.created = data.getCreated();
		this.creator = data.getCreator().getEmail();
		
		this.name = data.getName();
		this.description = data.getDescription();
		this.vcsType = data.getVcsType();
		this.repositoryUrl = data.getRepositoryURL();
		this.pomDirectory = data.getPomDirectory();
		this.mavenGoals = data.getMavenGoals();
		this.startupFile = data.getStartupFile();
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public void setId(int id) {
		this.id = id;
	}

	@Override
	public Date getCreated() {
		return created;
	}

	@Override
	public String getCreator() {
		return creator;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public VCSType getVcsType() {
		return vcsType;
	}

	@Override
	public void setVcsType(VCSType vcsType) {
		this.vcsType = vcsType;
	}

	@Override
	public String getRepositoryUrl() {
		return repositoryUrl;
	}

	@Override
	public void setRepositoryUrl(String repositoryURL) {
		this.repositoryUrl = repositoryURL;
	}

	@Override
	public String getPomDirectory() {
		return pomDirectory;
	}

	@Override
	public void setPomDirectory(String pomDirectory) {
		this.pomDirectory = pomDirectory;
	}

	@Override
	public String getMavenGoals() {
		return mavenGoals;
	}

	@Override
	public void setMavenGoals(String mavenGoals) {
		this.mavenGoals = mavenGoals;
	}

	@Override
	public String getStartupFile() {
		return startupFile;
	}

	@Override
	public void setStartupFile(String startupFile) {
		this.startupFile = startupFile;
	}
}
