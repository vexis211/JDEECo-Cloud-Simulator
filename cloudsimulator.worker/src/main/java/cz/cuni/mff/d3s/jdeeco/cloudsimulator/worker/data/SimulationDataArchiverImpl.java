package cz.cuni.mff.d3s.jdeeco.cloudsimulator.worker.data;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class SimulationDataArchiverImpl implements SimulationDataArchiver {

	@Override
	public void extract(InputStream archiveStream, String targetDir) {
		ZipInputStream zipStream = new ZipInputStream(archiveStream);
//		zipStream.
		
		 // TODO implement
	}

	@Override
	public OutputStream compress(String sourceDir) {
		InputStream outputStream = null; // TODO implement
//		ZipOutputStream zipStream = new ZipOutputStream(outputStream);
		
		
		return null;
	}

}
