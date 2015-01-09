package cz.cuni.mff.d3s.jdeeco.cloudsimulator.worker.data;

import java.io.InputStream;

public interface SimulationDataLoader {
	InputStream download(String source);
	void uploadLogs(String sourcePath, String target);
	void uploadResults(String sourcePath, String target);
}
