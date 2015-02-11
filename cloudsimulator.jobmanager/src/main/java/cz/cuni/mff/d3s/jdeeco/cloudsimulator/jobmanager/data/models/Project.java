package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.models;

import java.util.HashSet;
import java.util.Set;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.models.User;

public class Project implements java.io.Serializable {

	private static final long serialVersionUID = 5284213003683747912L;

	private Integer id;
	private User creator;
	private String name;
	private String description;
	private Set<SimulationConfiguration> simulationConfigurations = new HashSet<SimulationConfiguration>(0);
	private Set<User> visibleForUsers;

	public Project() {
	}

	public Project(User creator, String name, String description) {
		this.creator = creator;
		this.name = name;
		this.description = description;
	}

	public Project(User creator, String name, String description,
			Set<SimulationConfiguration> simulationConfigurations, Set<User> visibleForUsers) {
		this.creator = creator;
		this.name = name;
		this.description = description;
		this.simulationConfigurations = simulationConfigurations;
		this.setVisibleForUsers(visibleForUsers);
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

	public Set<SimulationConfiguration> getSimulationConfigurations() {
		return this.simulationConfigurations;
	}

	public void setSimulationConfigurations(Set<SimulationConfiguration> simulationConfigurations) {
		this.simulationConfigurations = simulationConfigurations;
	}

	public Set<User> getVisibleForUsers() {
		return visibleForUsers;
	}

	public void setVisibleForUsers(Set<User> visibleForUsers) {
		this.visibleForUsers = visibleForUsers;
	}

	@Override
	public int hashCode() {
		int result = 31 * 1 + (id == null ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Project other = (Project) obj;
		if (id == null) {
			if (other.id != null) return false;
		} else if (!id.equals(other.id)) return false;
		return true;
	}
}
