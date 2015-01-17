package cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.cloud;

import java.io.File;
import java.io.InputStream;

import org.openstack4j.model.common.DLPayload;
import org.openstack4j.model.common.payloads.FilePayload;

public class OpenStackDataService extends OpenStackComponent implements CloudDataService {

	public OpenStackDataService(OpenStackConnector openStackConnector) {
		super(openStackConnector);
	}

	@Override
	public InputStream download(String container, String name) {
		DLPayload payload = getClient().objectStorage().objects().download(container, name);
		return payload.getInputStream();
	}

	@Override
	public void upload(File sourceFile, String targetContainter, String targetName) {
		FilePayload payload = new FilePayload(sourceFile);
		getClient().objectStorage().objects().put(targetContainter, targetName, payload);
	}
}
