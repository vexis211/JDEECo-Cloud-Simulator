package cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.models;

import java.util.Date;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.data.SimulationStatus;

public class SimulationRun implements java.io.Serializable {

	private static final long serialVersionUID = -5788099497333128349L;

	private Integer id;
	private SimulationExecution simulationExecution;
	private SimulationStatus status = SimulationStatus.Created;
	private Date started;
	private Date ended;

	public SimulationRun() {
	}

	public SimulationRun(SimulationExecution simulationExecution) {
		this.simulationExecution = simulationExecution;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public SimulationExecution getSimulationExecution() {
		return this.simulationExecution;
	}

	public void setSimulationExecution(SimulationExecution simulationExecution) {
		this.simulationExecution = simulationExecution;
	}

	public SimulationStatus getStatus() {
		return this.status;
	}

	public void setStatus(SimulationStatus status) {
		this.status = status;
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
}
