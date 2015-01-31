package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.planning;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.workers.WorkerInstance;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.workers.WorkerManager;

public class WorkerPlanFactoryImpl implements WorkerPlanFactory {

	private final WorkerManager workerManager;
	private final WorkerPlanIdGenerator workerPlanIdGenerator;

	public WorkerPlanFactoryImpl(WorkerManager workerManager, WorkerPlanIdGenerator workerPlanIdGenerator) {
		this.workerManager = workerManager;
		this.workerPlanIdGenerator = workerPlanIdGenerator;
	}

	@Override
	public WorkerPlan create(WorkerInstance worker, WorkerPlanChangeListener changeListener) {
		return new WorkerPlanImpl(workerPlanIdGenerator.generate(), worker, changeListener, workerManager.getStatistics());
	}
}
