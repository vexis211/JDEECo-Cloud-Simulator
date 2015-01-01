package cz.cuni.mff.d3s.jdeeco.cloudsimulator.worker.data;

public interface SimulationDataListener {

	void dataPrepared(int simulationRunId, SimulationData data);
	void resultsSaved(int simulationRunId);
}
