package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.planning;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.workers.WorkerInstance;

public interface WorkerPlanFactory {
	WorkerPlan create(WorkerInstance worker, WorkerPlanChangeListener changeListener);
}
