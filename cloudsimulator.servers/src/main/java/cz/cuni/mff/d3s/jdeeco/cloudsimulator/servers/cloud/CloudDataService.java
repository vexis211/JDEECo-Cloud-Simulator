package cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.cloud;

import java.io.File;
import java.io.InputStream;

public interface CloudDataService {

	InputStream download(String container, String name);

	void upload(File sourceFile, String targetContainter, String targetName);
}
