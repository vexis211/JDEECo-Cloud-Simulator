package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.workers;

import java.util.List;

import org.joda.time.DateTime;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.updates.WorkerStatusUpdate;

public interface WorkerManager {

	int getCurrentWorkerCount();
	void setDesiredWorkerCount(int desiredCreatedWorkerCount, int desiredRunningWorkerCount);

	DateTime getNewWorkerStartedTime();
	WorkerInstance startNewWorker();
	void stopWorker(WorkerInstance worker);
	
	List<WorkerInstance> listWorkers();
		
	void update(List<WorkerStatusUpdate> updates);
	
	WorkerStatistics getStatistics();
}
