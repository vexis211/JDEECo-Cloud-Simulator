package cz.cuni.mff.d3s.jdeeco.cloudsimulator.worker.data;

import java.io.InputStream;

public interface SimulationDataLoader {
	InputStream downloadPackage(int executionId);
	void uploadLogs(String sourcePath, int runId);
	void uploadResults(String sourcePath, int runId);
}
