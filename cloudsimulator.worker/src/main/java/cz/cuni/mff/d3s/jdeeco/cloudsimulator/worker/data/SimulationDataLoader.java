package cz.cuni.mff.d3s.jdeeco.cloudsimulator.worker.data;

import java.io.InputStream;

public interface SimulationDataLoader {
	InputStream download(String source);
	void upload(String sourceUri, String target);
}
