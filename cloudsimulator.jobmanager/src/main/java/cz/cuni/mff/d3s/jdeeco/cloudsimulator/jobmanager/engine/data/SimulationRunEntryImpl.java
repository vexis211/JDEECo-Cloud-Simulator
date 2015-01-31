package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.data;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.models.SimulationRun;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.SimulationStatus;

public class SimulationRunEntryImpl implements SimulationRunEntry {

	private final SimulationRun data;
	private final SimulationExecutionEntry executionEntry;
	
	private SimulationStatus status;

	public SimulationRunEntryImpl(SimulationRun data, SimulationExecutionEntry executionEntry) {
		this.data = data;
		this.executionEntry = executionEntry;
	}

	@Override
	public int getId() {
		return this.data.getId();
	}

	@Override
	public SimulationExecutionEntry getExecution() {
		return executionEntry;
	}

	@Override
	public SimulationStatus getStatus() {
		return this.status;
	}

	@Override
	public void setStatus(SimulationStatus status) {
		this.status = status;
	}
}
