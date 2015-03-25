package cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web.client.data;

import java.util.Date;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.models.SimulationRun;

public class SimulationRunItemImpl implements SimulationRunItem {

	private int id;
	private String status;
	private Date startedDate;
	private Date endedDate;

	
	public SimulationRunItemImpl() {
	}
	
	public SimulationRunItemImpl(SimulationRun run) {
		this.id = run.getId();
		//this.status 
		// TODO add to run
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public String getStatus() {
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
