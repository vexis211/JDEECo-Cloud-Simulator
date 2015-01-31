package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.planning;

public class SimulationPlanFactoryImpl implements SimulationPlanFactory {

	private final WorkerPlanFactory workerPlanFactory;

	public SimulationPlanFactoryImpl(WorkerPlanFactory workerPlanFactory) {
		this.workerPlanFactory = workerPlanFactory;
	}

	@Override
	public SimulationPlan create() {
		return new SimulationPlanImpl(workerPlanFactory);
	}
}
