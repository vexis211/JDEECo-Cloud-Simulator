package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.workers;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.joda.time.DateTime;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.data.WorkerStatus;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.cloud.CloudMachine;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.cloud.CloudMachineBuilderParams;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.cloud.CloudMachineService;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.JobStatistics;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.SafeJobStatisticsImpl;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.updates.WorkerStatusUpdate;

public class WorkerManagerImpl implements WorkerManager {

	private final Logger logger = LoggerFactory.getLogger(WorkerManagerImpl.class);

	private final HashMap<String, WorkerInstance> runningWorkersById = new HashMap<>();
	private final HashMap<String, WorkerInstance> stoppedWorkersById = new HashMap<>();
	private final CloudMachineService cloudMachineService;
	private final CloudMachineBuilderParams workerBuilderParams;
	private final WorkerIdGenerator workerIdGenerator;
	private final JobStatistics<String> workerStartStatistics;
	private final JobStatistics<String> workerCreateAndStartStatistics;

	private int desiredCreatedWorkerCount = 3; // default value
	private int desiredRunningWorkerCount = 1; // default value

	public WorkerManagerImpl(CloudMachineService cloudMachineService, CloudMachineBuilderParams workerBuilderParams,
			WorkerIdGenerator workerIdGenerator) {
		this.cloudMachineService = cloudMachineService;
		this.workerBuilderParams = workerBuilderParams;
		this.workerIdGenerator = workerIdGenerator;

		this.workerStartStatistics = new SafeJobStatisticsImpl<String>();
		this.workerCreateAndStartStatistics = new SafeJobStatisticsImpl<String>();

		initializeWorkerMap();
		checkDesiredWorkerCounts();
	}

	private void initializeWorkerMap() {
		logger.info("Initializing worker manager from cloud machine servis...");

		for (CloudMachine cloudMachine : cloudMachineService.listMachines()) {
			WorkerInstance worker = getWorker(cloudMachine);
			if (worker.getStatus() == WorkerStatus.Stopped) {
				stoppedWorkersById.put(worker.getWorkerId(), worker);
			} else {
				runningWorkersById.put(worker.getWorkerId(), worker);
			}
		}
	}

	private WorkerInstance getWorker(CloudMachine cloudMachine) {
		return new WorkerInstanceImpl(cloudMachine);
	}

	@Override
	public int getCurrentAvailableWorkerCount() {
		return runningWorkersById.size();
	}

	@Override
	public List<WorkerInstance> listAvailableWorkers() {
		return runningWorkersById.values().stream().collect(Collectors.toList());
	}

	@Override
	public JobStatistics<String> getWorkerStartStatistics() {
		return workerStartStatistics;
	}

	@Override
	public DateTime whenWorkerWillBePrepared() {
		// TODO improvement - here is place for estimate optimization
		if (stoppedWorkersById.isEmpty()) {
			return DateTime.now().plus(workerCreateAndStartStatistics.getAverageJobTimeInMillis());
		} else {
			return DateTime.now().plus(workerStartStatistics.getAverageJobTimeInMillis());
		}
	}

	@Override
	public WorkerInstance startWorker() {
		WorkerInstance workerToStart;
		if (stoppedWorkersById.isEmpty()) {
			// create new worker
			String workerId = workerIdGenerator.generate();
			workerCreateAndStartStatistics.jobStarted(workerId);
			CloudMachine cloudMachine = cloudMachineService.buildMachineFromTemplate(workerId, workerBuilderParams)
					.build();
			workerToStart = getWorker(cloudMachine);
		} else {
			// get stopped worker
			String workerId = stoppedWorkersById.entrySet().stream().findAny().get().getKey();
			workerToStart = stoppedWorkersById.remove(workerId);
			workerStartStatistics.jobStarted(workerToStart.getWorkerId());
			cloudMachineService.startMachine(workerToStart.getCloudMachine());
		}

		runningWorkersById.put(workerToStart.getWorkerId(), workerToStart);
		workerToStart.setStatus(WorkerStatus.Starting);

		return workerToStart;
	}

	@Override
	public boolean stopWorker(WorkerInstance worker) {
		if (getCurrentAvailableWorkerCount() <= desiredRunningWorkerCount) {
			return false;
		}

		cloudMachineService.stopMachine(worker.getCloudMachine());
		worker.setStatus(WorkerStatus.Stopped);

		runningWorkersById.remove(worker.getWorkerId());
		stoppedWorkersById.put(worker.getWorkerId(), worker);

		return true;
	}

	@Override
	public void update(List<WorkerStatusUpdate> updates) {
		for (WorkerStatusUpdate workerStatusUpdate : updates) {
			String workerId = workerStatusUpdate.getWorkerId();
			WorkerInstance worker = runningWorkersById.get(workerId);

			worker.setStatus(workerStatusUpdate.getWorkerStatus());

			if (workerStatusUpdate.getWorkerStatus() == WorkerStatus.Started) {
				workerStartStatistics.jobCompleted(workerId);
				workerCreateAndStartStatistics.jobCompleted(workerId);
			}
		}
	}

	@Override
	public void setDesiredWorkerCount(int desiredCreatedWorkerCount, int desiredRunningWorkerCount) {
		this.desiredCreatedWorkerCount = desiredCreatedWorkerCount;
		this.desiredRunningWorkerCount = desiredRunningWorkerCount;

		checkDesiredWorkerCounts();
	}

	private void checkDesiredWorkerCounts() {

		// create/delete workers to get desired number of created workers
		int workerCountDiff = desiredCreatedWorkerCount - (runningWorkersById.size() + stoppedWorkersById.size());

		// (create and) start workers - even workers which should be only created needs to be started to initialize,
		if (workerCountDiff > 0) {
			for (int i = 0; i < workerCountDiff; i++) {
				if (startWorker() == null) {
					break;
				}
			}
		}
		// delete workers, which are stopped and are not needed
		else if (workerCountDiff < 0) {
			for (int i = 0; i < -workerCountDiff; i++) {
				if (!deleteStoppedWorker()) {
					break;
				}
			}
		}

		// simulation scheduler stops workers, which it does not need!!!!!!!!!!!!!!!!!!!!!!!!!!
	}

	private boolean deleteStoppedWorker() {
		if (!stoppedWorkersById.isEmpty()) {
			Entry<String, WorkerInstance> entry = stoppedWorkersById.entrySet().stream().findAny().get();
			runningWorkersById.remove(entry.getKey());
			cloudMachineService.deleteMachine(entry.getValue().getCloudMachine());
			return true;
		}

		return false;
	}

}