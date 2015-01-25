package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.data;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.models.SimulationRun;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.SimulationStatus;

public class SimulationRunEntryImpl implements SimulationRunEntry {

	private final SimulationRun data;
	private SimulationStatus status;

	public SimulationRunEntryImpl(SimulationRun data) {
		this.data = data;
	}

	@Override
	public int getId() {
		return this.data.getId();
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
