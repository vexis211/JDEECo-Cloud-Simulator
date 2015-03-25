package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.planning;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.workers.WorkerInstance;

public interface WorkerPlanFactory {
	WorkerPlan create(WorkerInstance worker, WorkerPlanChangeListener changeListener);
}
