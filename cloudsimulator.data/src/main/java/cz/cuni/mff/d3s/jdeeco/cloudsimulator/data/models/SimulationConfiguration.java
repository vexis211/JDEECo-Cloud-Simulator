package cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.models;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class SimulationConfiguration implements java.io.Serializable {

	private static final long serialVersionUID = 4700118663159508565L;

	private int id;
	private Project project;
	private SimulationData simulationData;
	private User creator;
	private String name;
	private String description;
	private Date created;
	private int defaultRunCount;
	private Set<SimulationExecution> simulationExecutions = new HashSet<SimulationExecution>(0);

	public SimulationConfiguration() {
	}

	public SimulationConfiguration(Project project, User creator, SimulationData simulationData, String name,
			String description, int defaultRunCount) {
		this.project = project;
		this.creator = creator;
		this.simulationData = simulationData;
		this.name = name;
		this.description = description;
		this.defaultRunCount = defaultRunCount;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Project getProject() {
		return this.project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public SimulationData getSimulationData() {
		return this.simulationData;
	}

	public void setSimulationData(SimulationData simulationData) {
		this.simulationData = simulationData;
	}

	public User getCreator() {
		return this.creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return this.description;
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

	public int getDefaultRunCount() {
		return defaultRunCount;
	}

	public void setDefaultRunCount(int defaultRunCount) {
		this.defaultRunCount = defaultRunCount;
	}

	public Set<SimulationExecution> getSimulationExecutions() {
		return this.simulationExecutions;
	}

	public void setSimulationExecutions(Set<SimulationExecution> simulationExecutions) {
		this.simulationExecutions = simulationExecutions;
	}

}
