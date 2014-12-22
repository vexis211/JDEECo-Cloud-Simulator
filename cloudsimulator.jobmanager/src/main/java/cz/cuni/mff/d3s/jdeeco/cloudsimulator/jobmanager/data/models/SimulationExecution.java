package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.models;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.SimulationStatus;

public class SimulationExecution implements java.io.Serializable {

	private static final long serialVersionUID = -7158964600876801749L;

	private Integer id;
	private SimulationConfiguration simulationConfiguration;
	private User creator;
	private String description;
	private SimulationStatus status;
	private Date created;
	private Date started;
	private Date ended;
	private Set<SimulationRun> simulationRuns = new HashSet<SimulationRun>(0);

	public SimulationExecution() {
	}

	public SimulationExecution(SimulationConfiguration simulationConfiguration, User creator, String description,
			SimulationStatus status, Date created) {
		this.simulationConfiguration = simulationConfiguration;
		this.creator = creator;
		this.description = description;
		this.status = status;
		this.created = created;
	}

	public SimulationExecution(SimulationConfiguration simulationConfiguration, User creator, String description,
			SimulationStatus status, Date created, Date started, Date ended, Set<SimulationRun> simulationRuns) {
		this.simulationConfiguration = simulationConfiguration;
		this.creator = creator;
		this.description = description;
		this.status = status;
		this.created = created;
		this.started = started;
		this.ended = ended;
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

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public SimulationStatus getStatus() {
		return this.status;
	}

	public void setStatus(SimulationStatus status) {
		this.status = status;
	}

	public Date getCreated() {
		return this.created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getStarted() {
		return this.started;
	}

	public void setStarted(Date started) {
		this.started = started;
	}

	public Date getEnded() {
		return this.ended;
	}

	public void setEnded(Date ended) {
		this.ended = ended;
	}
	
	public Set<SimulationRun> getSimulationRuns() {
		return this.simulationRuns;
	}

	public void setSimulationRuns(Set<SimulationRun> simulationRuns) {
		this.simulationRuns = simulationRuns;
	}
}
