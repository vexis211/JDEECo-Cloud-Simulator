package cz.cuni.mff.d3s.jdeeco.cloudsimulator.worker.data;

import java.io.InputStream;
import java.io.OutputStream;

public interface SimulationDataArchiver {

	void extract(InputStream archiveStream, String targetDir);

	OutputStream compress(String sourceDir);
}
