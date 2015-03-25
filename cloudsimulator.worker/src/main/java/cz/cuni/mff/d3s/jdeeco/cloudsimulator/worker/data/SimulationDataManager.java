package cz.cuni.mff.d3s.jdeeco.cloudsimulator.worker.data;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.Disposable;


public interface SimulationDataManager extends Disposable {

	void prepareData(int simulationRunId);

	void saveResults(int simulationRunId, SimulationData data);
}
