package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.planning;

import org.joda.time.DateTime;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.data.SimulationRunEntry;

public class WorkerPlanItemImpl implements WorkerPlanItem {

	private final SimulationRunEntry simulationRun;
	private final DateTime startTime;
	private final DateTime endTime;

	public WorkerPlanItemImpl(SimulationRunEntry simulationRun, DateTime startTime, DateTime endTime) {
		this.simulationRun = simulationRun;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	@Override
	public SimulationRunEntry getSimulationRun() {
		return simulationRun;
	}

	@Override
	public DateTime getStartTime() {
		return startTime;
	}

	@Override
	public DateTime getEndTime() {
		return endTime;
	}
}
