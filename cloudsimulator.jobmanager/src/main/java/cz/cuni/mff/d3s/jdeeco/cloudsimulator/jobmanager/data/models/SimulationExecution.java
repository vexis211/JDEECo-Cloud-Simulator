package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.models;

import java.util.HashSet;
import java.util.Set;

public class SimulationExecution implements java.io.Serializable {

	private static final long serialVersionUID = -7158964600876801749L;

	private Integer id;
	private SimulationConfiguration simulationConfiguration;
	private User creator;
	private Set<SimulationRun> simulationRuns = new HashSet<SimulationRun>(0);

	public SimulationExecution() {
	}

	public SimulationExecution(SimulationConfiguration simulationConfiguration, User creator) {
		this.simulationConfiguration = simulationConfiguration;
		this.creator = creator;
	}

	public SimulationExecution(SimulationConfiguration simulationConfiguration, User creator,
			Set<SimulationRun> simulationRuns) {
		this.simulationConfiguration = simulationConfiguration;
		this.creator = creator;
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

	public User getCreator() {
		return this.creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	public Set<SimulationRun> getSimulationRuns() {
		return this.simulationRuns;
	}

	public void setSimulationRuns(Set<SimulationRun> simulationRuns) {
		this.simulationRuns = simulationRuns;
	}

}
