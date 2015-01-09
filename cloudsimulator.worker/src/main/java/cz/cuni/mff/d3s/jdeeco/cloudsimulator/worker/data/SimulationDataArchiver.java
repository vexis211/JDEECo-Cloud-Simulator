package cz.cuni.mff.d3s.jdeeco.cloudsimulator.worker.data;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface SimulationDataArchiver {

	void extract(InputStream archiveStream, String targetDir) throws IOException;

	void compress(String sourcePath, OutputStream outputStream) throws IOException;
}
