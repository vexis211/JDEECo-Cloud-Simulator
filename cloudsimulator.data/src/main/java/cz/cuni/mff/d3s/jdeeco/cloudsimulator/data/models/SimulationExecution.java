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
	private int runMultiplicator;
	private String runProfile;
	private String statisticsProfile;
	private String assertsProfile;
	private Set<SimulationRun> simulationRuns = new HashSet<SimulationRun>(0);
	private Set<SimulationExecutionStatistic> simulationExecutionStatistics = new HashSet<SimulationExecutionStatistic>(
			0);

	public SimulationExecution() {
	}

	public SimulationExecution(SimulationConfiguration simulationConfiguration, User creator, String description,
			SimulationStatus status, Date created, Date ended, ExecutionEndSpecificationType endSpecificationType,
			Date endDate, int runMultiplicator, String runProfile, String statisticsProfile, String assertsProfile) {
		this.simulationConfiguration = simulationConfiguration;
		this.creator = creator;
		this.description = description;
		this.status = status;
		this.created = created;
		this.endSpecificationType = endSpecificationType;
		this.endDate = endDate;
		this.runMultiplicator = runMultiplicator;
		this.runProfile = runProfile;
		this.statisticsProfile = statisticsProfile;
		this.assertsProfile = assertsProfile;
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

	public int getRunMultiplicator() {
		return runMultiplicator;
	}

	public void setRunMultiplicator(int runMultiplicator) {
		this.runMultiplicator = runMultiplicator;
	}

	public String getRunProfile() {
		return runProfile;
	}

	public void setRunProfile(String runProfile) {
		this.runProfile = runProfile;
	}

	public String getStatisticsProfile() {
		return statisticsProfile;
	}

	public void setStatisticsProfile(String statisticsProfile) {
		this.statisticsProfile = statisticsProfile;
	}

	public String getAssertsProfile() {
		return assertsProfile;
	}

	public void setAssertsProfile(String assertsProfile) {
		this.assertsProfile = assertsProfile;
	}

	public Set<SimulationRun> getSimulationRuns() {
		return this.simulationRuns;
	}

	public void setSimulationRuns(Set<SimulationRun> simulationRuns) {
		this.simulationRuns = simulationRuns;
	}

	public Set<SimulationExecutionStatistic> getSimulationExecutionStatistics() {
		return this.simulationExecutionStatistics;
	}

	public void setSimulationExecutionStatistics(Set<SimulationExecutionStatistic> simulationExecutionStatistics) {
		this.simulationExecutionStatistics = simulationExecutionStatistics;
	}
}
