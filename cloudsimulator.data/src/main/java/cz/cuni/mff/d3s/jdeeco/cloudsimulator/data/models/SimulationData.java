package cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.models;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.data.VCSType;

public class SimulationData implements java.io.Serializable {

	private static final long serialVersionUID = -5013786220965169776L;

	private Integer id;
	private User creator;
	private Project project;
	private String name;
	private String description;
	private Date created;
	private VCSType vcsType;
	private String repositoryURL;
	private String pomDirectory;
	private String mavenGoals;
	private String startupFile;

	private Set<SimulationConfiguration> simulationConfigurations = new HashSet<SimulationConfiguration>(0);

	public SimulationData() {
	}

	public SimulationData(User creator, Project project, String name, String description, VCSType vcsType, String repositoryURL,
			String pomDirectory, String mavenGoals, String startupFile) {
		this.creator = creator;
		this.project = project;
		this.name = name;
		this.description = description;
		this.vcsType = vcsType;
		this.repositoryURL = repositoryURL;
		this.pomDirectory = pomDirectory;
		this.mavenGoals = mavenGoals;
		this.startupFile = startupFile;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public User getCreator() {
		return this.creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	public Project getProject() {
		return this.project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public VCSType getVcsType() {
		return vcsType;
	}

	public void setVcsType(VCSType vcsType) {
		this.vcsType = vcsType;
	}

	public String getRepositoryURL() {
		return repositoryURL;
	}

	public void setRepositoryURL(String repositoryURL) {
		this.repositoryURL = repositoryURL;
	}

	public String getPomDirectory() {
		return pomDirectory;
	}

	public void setPomDirectory(String pomDirectory) {
		this.pomDirectory = pomDirectory;
	}

	public String getMavenGoals() {
		return mavenGoals;
	}

	public void setMavenGoals(String mavenGoals) {
		this.mavenGoals = mavenGoals;
	}

	public String getStartupFile() {
		return startupFile;
	}

	public void setStartupFile(String startupFile) {
		this.startupFile = startupFile;
	}
	
	public Set<SimulationConfiguration> getSimulationConfigurations() {
		return this.simulationConfigurations;
	}

	public void setSimulationConfigurations(Set<SimulationConfiguration> simulationConfigurations) {
		this.simulationConfigurations = simulationConfigurations;
	}
}
