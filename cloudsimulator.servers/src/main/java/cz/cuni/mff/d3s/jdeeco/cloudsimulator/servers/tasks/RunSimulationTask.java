package cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.tasks;

public interface RunSimulationTask extends WorkerTask {
	int getSimulationRunId();
	String getDataSource();
	String getLogsTarget();
	String getResultsTarget();
}
