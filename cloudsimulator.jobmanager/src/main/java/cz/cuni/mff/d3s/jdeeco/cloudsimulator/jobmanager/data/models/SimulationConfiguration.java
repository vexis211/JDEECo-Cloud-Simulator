package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.models;

import java.util.HashSet;
import java.util.Set;

public class SimulationConfiguration implements java.io.Serializable {

	private static final long serialVersionUID = 4700118663159508565L;

	private Integer id;
	private Project project;
	private SimulationData simulationData;
	private User creator;
	private String name;
	private Set<SimulationExecution> simulationExecutions = new HashSet<SimulationExecution>(0);

	public SimulationConfiguration() {
	}

	public SimulationConfiguration(Project project, User creator, String name) {
		this.project = project;
		this.creator = creator;
		this.name = name;
	}

	public SimulationConfiguration(Project project, SimulationData simulationData, User creator, String name,
			Set<SimulationExecution> simulationExecutions) {
		this.project = project;
		this.simulationData = simulationData;
		this.creator = creator;
		this.name = name;
		this.simulationExecutions = simulationExecutions;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
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

	public Set<SimulationExecution> getSimulationExecutions() {
		return this.simulationExecutions;
	}

	public void setSimulationExecutions(Set<SimulationExecution> simulationExecutions) {
		this.simulationExecutions = simulationExecutions;
	}

}
