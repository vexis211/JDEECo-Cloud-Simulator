package cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.cloud;

import java.io.File;
import java.io.InputStream;

import org.openstack4j.model.common.DLPayload;
import org.openstack4j.model.common.payloads.FilePayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OpenStackDataService extends OpenStackComponent implements CloudDataService {

	private static final Logger logger = LoggerFactory.getLogger(OpenStackDataService.class);

	public OpenStackDataService(OpenStackConnector openStackConnector) {
		super(openStackConnector);
	}

	@Override
	public InputStream download(String container, String name) {
		logger.info("Downloading data from open stack object storage. Container: '{}', name: '{}'.", container, name);

		DLPayload payload = getClient().objectStorage().objects().download(container, name);
		return payload.getInputStream();
	}

	@Override
	public void upload(File sourceFile, String targetContainter, String targetName) {
		logger.info("Uploading file to open stack object storage. Source file: '{}', container: '{}', name: '{}'.",
				sourceFile, targetContainter, targetName);

		FilePayload payload = new FilePayload(sourceFile);
		getClient().objectStorage().objects().put(targetContainter, targetName, payload);
	}
}
