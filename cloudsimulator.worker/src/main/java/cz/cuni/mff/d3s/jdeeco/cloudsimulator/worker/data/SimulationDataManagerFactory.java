package cz.cuni.mff.d3s.jdeeco.cloudsimulator.worker.data;

public interface SimulationDataManagerFactory {

	SimulationDataManager create(String dataParentDirectory, String logParentDirectory, SimulationDataListener listener);
}
