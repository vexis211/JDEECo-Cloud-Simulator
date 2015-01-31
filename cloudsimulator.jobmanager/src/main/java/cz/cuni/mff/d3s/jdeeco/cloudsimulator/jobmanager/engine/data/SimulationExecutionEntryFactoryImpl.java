package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.data;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.models.SimulationExecution;

public class SimulationExecutionEntryFactoryImpl implements SimulationExecutionEntryFactory {

	private SimulationRunEntryFactory simulationRunEntryFactory;

	public SimulationExecutionEntryFactoryImpl(SimulationRunEntryFactory simulationRunEntryFactory) {
		this.simulationRunEntryFactory = simulationRunEntryFactory;
	}

	@Override
	public SimulationExecutionEntry create(SimulationExecution data) {
		return new SimulationExecutionEntryImpl(data, new SimulationExecutionStatisticsImpl(),
				simulationRunEntryFactory);
	}
}