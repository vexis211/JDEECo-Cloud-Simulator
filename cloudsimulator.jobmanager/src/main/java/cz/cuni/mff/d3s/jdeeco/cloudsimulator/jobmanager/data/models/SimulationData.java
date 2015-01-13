package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.models;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.vcs.VCSType;

public class SimulationData implements java.io.Serializable {

	private static final long serialVersionUID = -5013786220965169776L;

	private Integer id;
	private User creator;
	private String description;
	private Date created;
	private VCSType vcsType;
	private String repositoryURL;
	private String pathToPom;
	private String mavenGoals;

	private Set<SimulationConfiguration> simulationConfigurations = new HashSet<SimulationConfiguration>(0);

	public SimulationData() {
	}

	public SimulationData(User creator, String description, Date created, VCSType vcsType, String repositoryURL,
			String pathToPom, String mavenGoals) {
		this.creator = creator;
		this.description = description;
		this.created = created;
		this.vcsType = vcsType;
		this.repositoryURL = repositoryURL;
		this.pathToPom = pathToPom;
		this.mavenGoals = mavenGoals;
	}

	public SimulationData(User creator, String description, Date created, VCSType vcsType, String repositoryURL,
			String pathToPom, String mavenGoals, Set<SimulationConfiguration> simulationConfigurations) {
		this.creator = creator;
		this.description = description;
		this.created = created;
		this.vcsType = vcsType;
		this.repositoryURL = repositoryURL;
		this.pathToPom = pathToPom;
		this.mavenGoals = mavenGoals;
		this.simulationConfigurations = simulationConfigurations;
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

	public String getPathToPom() {
		return pathToPom;
	}

	public void setPathToPom(String pathToPom) {
		this.pathToPom = pathToPom;
	}

	public String getMavenGoals() {
		return mavenGoals;
	}

	public void setMavenGoals(String mavenGoals) {
		this.mavenGoals = mavenGoals;
	}
	
	public Set<SimulationConfiguration> getSimulationConfigurations() {
		return this.simulationConfigurations;
	}

	public void setSimulationConfigurations(Set<SimulationConfiguration> simulationConfigurations) {
		this.simulationConfigurations = simulationConfigurations;
	}

}
