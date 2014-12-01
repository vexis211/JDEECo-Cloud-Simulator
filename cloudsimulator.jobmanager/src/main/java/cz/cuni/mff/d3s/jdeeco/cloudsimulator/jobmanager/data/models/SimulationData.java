package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.models;

import java.util.HashSet;
import java.util.Set;

public class SimulationData implements java.io.Serializable {

	private static final long serialVersionUID = -5013786220965169776L;

	private Integer id;
	private User creator;
	private Set<SimulationConfiguration> simulationConfigurations = new HashSet<SimulationConfiguration>(0);

	public SimulationData() {
	}

	public SimulationData(User creator) {
		this.creator = creator;
	}

	public SimulationData(User creator, Set<SimulationConfiguration> simulationConfigurations) {
		this.creator = creator;
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

	public Set<SimulationConfiguration> getSimulationConfigurations() {
		return this.simulationConfigurations;
	}

	public void setSimulationConfigurations(Set<SimulationConfiguration> simulationConfigurations) {
		this.simulationConfigurations = simulationConfigurations;
	}

}
