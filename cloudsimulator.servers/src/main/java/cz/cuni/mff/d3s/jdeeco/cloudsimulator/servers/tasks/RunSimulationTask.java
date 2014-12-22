package cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.tasks;

public interface RunSimulationTask extends WorkerTask {
	int getSimulationRunId();
	String getDataUri();
	String getLogFileUri();
	String getResultsUri();
}
