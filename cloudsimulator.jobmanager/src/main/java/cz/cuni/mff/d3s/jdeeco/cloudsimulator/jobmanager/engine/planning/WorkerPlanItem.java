package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.planning;

import org.joda.time.DateTime;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.data.SimulationRunEntry;

public interface WorkerPlanItem {
	SimulationRunEntry getSimulationRun();
	
	DateTime getStartTime();
	DateTime getEndTime();
}
