package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.models;

// Generated Nov 19, 2014 7:40:52 PM by Hibernate Tools 4.0.0

import java.util.HashSet;
import java.util.Set;

/**
 * SimulationExecution generated by hbm2java
 */
public class SimulationExecution implements java.io.Serializable {

	private Integer id;
	private SimulationConfiguration simulationConfiguration;
	private User user;
	private Set<SimulationRun> simulationRuns = new HashSet<SimulationRun>(0);

	public SimulationExecution() {
	}

	public SimulationExecution(SimulationConfiguration simulationConfiguration, User user) {
		this.simulationConfiguration = simulationConfiguration;
		this.user = user;
	}

	public SimulationExecution(SimulationConfiguration simulationConfiguration, User user,
			Set<SimulationRun> simulationRuns) {
		this.simulationConfiguration = simulationConfiguration;
		this.user = user;
		this.simulationRuns = simulationRuns;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public SimulationConfiguration getSimulationConfiguration() {
		return this.simulationConfiguration;
	}

	public void setSimulationConfiguration(SimulationConfiguration simulationConfiguration) {
		this.simulationConfiguration = simulationConfiguration;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Set<SimulationRun> getSimulationRuns() {
		return this.simulationRuns;
	}

	public void setSimulationRuns(Set<SimulationRun> simulationRuns) {
		this.simulationRuns = simulationRuns;
	}

}
