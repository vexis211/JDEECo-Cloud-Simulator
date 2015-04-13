package cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web.client.data;

import java.util.Date;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.data.SimulationStatus;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.models.SimulationRun;

public class SimulationRunItemImpl implements SimulationRunItem {

	private int id;
	private SimulationStatus status;
	private Date startedDate;
	private Date endedDate;

	
	public SimulationRunItemImpl() {
	}
	
	public SimulationRunItemImpl(SimulationRun run) {
		this.id = run.getId();
		this.status = run.getStatus();
		this.startedDate = run.getStarted();
		this.endedDate = run.getEnded();
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public SimulationStatus getStatus() {
		return status;
	}

	@Override
	public Date getStartedDate() {
		return startedDate;
	}

	@Override
	public Date getEndedDate() {
		return endedDate;
	}
}
