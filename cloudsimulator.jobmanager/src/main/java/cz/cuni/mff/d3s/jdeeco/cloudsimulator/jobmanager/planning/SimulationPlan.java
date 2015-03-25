package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.planning;

import java.util.List;

import org.joda.time.DateTime;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.workers.WorkerInstance;

public interface SimulationPlan {
	WorkerPlan getPlanForFirstEndingWorker();
	WorkerPlan getPlanForLastEndingWorkerBefore(DateTime dateTime);
	
	WorkerPlan addWorkerPlan(WorkerInstance worker);
	void removeWorkerPlan(WorkerPlan workerPlan);

	boolean isAnythingPlanned();

	List<WorkerPlan> getWorkerPlans();
	int getWorkerPlanCount();
	
	void update(List<WorkerInstance> currentWorkers);
}
