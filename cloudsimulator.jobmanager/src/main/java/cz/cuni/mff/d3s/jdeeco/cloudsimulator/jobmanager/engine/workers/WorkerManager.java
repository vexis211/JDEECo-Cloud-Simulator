package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.workers;

import java.util.List;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.updates.WorkerStatusUpdate;

public interface WorkerManager {

	int getCurrentWorkerCount();
	void setDesiredWorkerCount(int desiredCreatedWorkerCount, int desiredRunningWorkerCount);
	
	List<WorkerInstance> listWorkers();
	List<WorkerInstance> listPreparedWorkers();
	List<WorkerInstance> listSimulatingWorkers();
		
	void update(List<WorkerStatusUpdate> updates);
	
	WorkerStatistics getStatistics();
}
