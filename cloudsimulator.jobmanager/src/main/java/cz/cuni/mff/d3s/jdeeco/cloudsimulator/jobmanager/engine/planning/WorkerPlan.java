package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.planning;

import org.joda.time.DateTime;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.workers.WorkerInstance;

public interface WorkerPlan extends Iterable<WorkerPlanItem> {	
	long getId();
	
	WorkerInstance getWorker();
	
	WorkerPlanItem getCurrentItem();
	DateTime getPlanEndTime();

	void addNextItem(WorkerPlanItem item);
	void update(WorkerInstance worker);
}
