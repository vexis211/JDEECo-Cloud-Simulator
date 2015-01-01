package cz.cuni.mff.d3s.jdeeco.cloudsimulator.worker.data;

import java.io.InputStream;
import java.io.OutputStream;

public interface SimulationDataLoader {
	InputStream download(String sourceUri);
	void upload(String sourceUri, String targetUri);
	void upload(OutputStream sourceStream, String targetUri);
}
