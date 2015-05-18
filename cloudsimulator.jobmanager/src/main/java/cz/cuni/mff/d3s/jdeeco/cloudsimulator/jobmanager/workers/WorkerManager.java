package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.workers;

import java.util.List;

import org.joda.time.DateTime;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.JobStatistics;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.updates.WorkerStatusUpdate;

public interface WorkerManager {

	int getCurrentAvailableWorkerCount();
	void setDesiredWorkerCount(int desiredCreatedWorkerCount, int desiredRunningWorkerCount);

	DateTime whenWorkerWillBePrepared();
	WorkerInstance startWorker();
	boolean stopWorker(WorkerInstance worker);
	
	List<WorkerInstance> listAvailableWorkers();
		
	void update(List<WorkerStatusUpdate> updates);
	
	JobStatistics<String> getWorkerStartStatistics();
}
