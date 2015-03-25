package cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.models;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.data.ExecutionEndSpecificationType;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.data.SimulationStatus;


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
    private ExecutionEndSpecificationType endSpecificationType;
	private Date endDate;
	private int runCount;
	private Set<SimulationRun> simulationRuns = new HashSet<SimulationRun>(0);

	public SimulationExecution() {
	}

	public SimulationExecution(SimulationConfiguration simulationConfiguration, User creator, String description,
			SimulationStatus status, Date created, Date ended, ExecutionEndSpecificationType endSpecificationType, Date endDate, int runCount) {
		this.simulationConfiguration = simulationConfiguration;
		this.creator = creator;
		this.description = description;
		this.status = status;
		this.created = created;
		this.endSpecificationType = endSpecificationType;
		this.endDate = endDate;
		this.runCount = runCount;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getRunCount() {
		return runCount;
	}

	public void setRunCount(int runCount) {
		this.runCount = runCount;
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

	public ExecutionEndSpecificationType getEndSpecificationType() {
		return endSpecificationType;
	}

	public void setEndSpecificationType(ExecutionEndSpecificationType endSpecificationType) {
		this.endSpecificationType = endSpecificationType;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	public Set<SimulationRun> getSimulationRuns() {
		return this.simulationRuns;
	}

	public void setSimulationRuns(Set<SimulationRun> simulationRuns) {
		this.simulationRuns = simulationRuns;
	}
}