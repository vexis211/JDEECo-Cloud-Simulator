package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.workers;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.joda.time.DateTime;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.cloud.CloudMachine;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.cloud.CloudMachineService;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.WorkerStatus;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.updates.WorkerStatusUpdate;

public class WorkerManagerImpl implements WorkerManager {

	private final HashMap<String, WorkerInstance> workersById = new HashMap<>();
	private final CloudMachineService cloudMachineService;
	private final String workerTemplateName;
	private final WorkerIdGenerator workerIdGenerator;
	private final WorkerStatistics statistics;
	
	private int desiredCreatedWorkerCount;
	private int desiredRunningWorkerCount;

	public WorkerManagerImpl(CloudMachineService cloudMachineService, String workerTemplateName,
			WorkerIdGenerator workerIdGenerator, WorkerStatisticsFactory statisticsFactory) {
		this.cloudMachineService = cloudMachineService;
		this.workerTemplateName = workerTemplateName;
		this.workerIdGenerator = workerIdGenerator;
		this.statistics = statisticsFactory.create();

		this.desiredCreatedWorkerCount = 3; // TODO to settings
		this.desiredRunningWorkerCount = 1; // TODO to settings
		
		initializeWorkerMap();
	}

	private void initializeWorkerMap() {
		for (CloudMachine cloudMachine : cloudMachineService.listMachines()) {
			WorkerInstance worker = getWorker(cloudMachine.getName(), cloudMachine);
			workersById.put(worker.getWorkerId(), worker);
		}
	}

	private WorkerInstance getWorker(String workerId, CloudMachine cloudMachine) {
		return new WorkerInstanceImpl(workerId, cloudMachine);
	}

	@Override
	public int getCurrentWorkerCount() {
		return workersById.size();
	}

	public int getRunningWorkerCount() {
		return workersById.size();
	}

	@Override
	public DateTime getNewWorkerStartedTime() {
		return DateTime.now().plus(statistics.getAverageStartTimeInMillis());
	}

	@Override
	public WorkerInstance startNewWorker() {
		String workerId = workerIdGenerator.generate();

		CloudMachine cloudMachine = cloudMachineService.buildMachineFromTemplate(workerTemplateName, workerId).build();
		WorkerInstance worker = getWorker(workerId, cloudMachine);
		setWorkerStatus(worker, WorkerStatus.Starting);
		return worker;
	}

	@Override
	public void stopWorker(WorkerInstance worker) {
		cloudMachineService.stopMachine(worker.getCloudMachine());
		setWorkerStatus(worker, WorkerStatus.Stopped);
	}

	@Override
	public List<WorkerInstance> listWorkers() {
		return workersById.values().stream().collect(Collectors.toList());
	}

	@Override
	public void update(List<WorkerStatusUpdate> updates) {
		for (WorkerStatusUpdate workerStatusUpdate : updates) {
			setWorkerStatus(workersById.get(workerStatusUpdate.getWorkerId()), workerStatusUpdate.getWorkerStatus());
		}
	}



	@Override
	public void setDesiredWorkerCount(int desiredCreatedWorkerCount, int desiredRunningWorkerCount) {
		this.desiredCreatedWorkerCount = desiredCreatedWorkerCount;
		this.desiredRunningWorkerCount = desiredRunningWorkerCount;
	
		checkDesiredWorkerCounts();
	}
	
	private void checkDesiredWorkerCounts() {

		int workerCountDiff = desiredCreatedWorkerCount - getCurrentWorkerCount();

		if (workerCountDiff > 0) {
			for (int i = 0; i < workerCountDiff; i++) {
				startNewWorker();
			}
		} else if (workerCountDiff < 0) {
			for (int i = 0; i < -workerCountDiff; i++) {
				boolean deleted = deleteNotSimulatingWorker();
				if (!deleted)
					break;
			}
		}

		int runningWorkerCountDiff = desiredRunningWorkerCount - getCurrentWorkerCount();

		if (runningWorkerCountDiff > 0) {
			for (int i = 0; i < runningWorkerCountDiff; i++) {
				boolean started = startStoppedWorker();
				if (!started)
					break;
			}
		} else if (runningWorkerCountDiff < 0) {
			for (int i = 0; i < -runningWorkerCountDiff; i++) {
				boolean stopped = stopNotSimulatingWorker();
				if (!stopped)
					break;
			}
		}
	}

	private boolean deleteNotSimulatingWorker() {
		Optional<WorkerInstance> stoppedMachineOptional = workersById.values().stream()
				.filter(x -> x.getStatus() == WorkerStatus.Stopped).findAny();
		if (stoppedMachineOptional.isPresent()) {
			WorkerInstance worker = stoppedMachineOptional.get();
			workersById.remove(worker.getWorkerId());
			cloudMachineService.deleteMachine(worker.getCloudMachine());
			return true;
		}

		Optional<WorkerInstance> startedMachineOptional = workersById.values().stream()
				.filter(x -> x.getStatus() == WorkerStatus.Started).findAny();
		if (startedMachineOptional.isPresent()) {
			WorkerInstance worker = startedMachineOptional.get();
			workersById.remove(worker.getWorkerId());
			cloudMachineService.deleteMachine(worker.getCloudMachine());
			return true;
		}

		return false;
	}

	private boolean startStoppedWorker() {
		Optional<WorkerInstance> stoppedMachineOptional = workersById.values().stream()
				.filter(x -> x.getStatus() == WorkerStatus.Stopped).findAny();
		if (stoppedMachineOptional.isPresent()) {
			WorkerInstance worker = stoppedMachineOptional.get();
			cloudMachineService.startMachine(worker.getCloudMachine());
			setWorkerStatus(worker, WorkerStatus.Started);
			return true;
		}

		return false;
	}

	private boolean stopNotSimulatingWorker() {
		Optional<WorkerInstance> startedMachineOptional = workersById.values().stream()
				.filter(x -> x.getStatus() == WorkerStatus.Started).findAny();
		if (startedMachineOptional.isPresent()) {
			WorkerInstance worker = startedMachineOptional.get();
			cloudMachineService.stopMachine(worker.getCloudMachine());
			setWorkerStatus(worker, WorkerStatus.Stopped);
			return true;
		}

		return false;
	}

	private void setWorkerStatus(WorkerInstance worker, WorkerStatus status) {
		worker.setStatus(status);

		switch (status) {
		case Starting:
			statistics.starting(worker.getWorkerId());
			break;
		case Started:
			statistics.started(worker.getWorkerId());
			break;
		default:
			break;
		}
	}

	@Override
	public WorkerStatistics getStatistics() {
		return statistics;
	}
}