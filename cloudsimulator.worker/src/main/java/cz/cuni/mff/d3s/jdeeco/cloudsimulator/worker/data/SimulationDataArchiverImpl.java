package cz.cuni.mff.d3s.jdeeco.cloudsimulator.worker.data;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.ZipHelper;

public class SimulationDataArchiverImpl implements SimulationDataArchiver {

	@Override
	public void extract(InputStream archiveStream, String targetDir) throws IOException {
		ZipHelper.unzip(archiveStream, new File(targetDir));
	}

	@Override
	public void compress(String sourcePath, OutputStream outputStream) throws IOException {
		ZipHelper.zipRecursively(new File(sourcePath), outputStream);
	}

}
