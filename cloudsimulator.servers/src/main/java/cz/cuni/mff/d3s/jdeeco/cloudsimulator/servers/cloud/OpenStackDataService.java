package cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.cloud;

import java.io.File;
import java.io.InputStream;

import org.openstack4j.api.OSClient;
import org.openstack4j.model.common.DLPayload;
import org.openstack4j.model.common.payloads.FilePayload;

public class OpenStackDataService implements CloudDataService {

	private final OpenStackConnector openStackConnector;
	private OSClient client;

	public OpenStackDataService(OpenStackConnector openStackConnector) {
		this.openStackConnector = openStackConnector;
	}

	private OSClient getClient() {
		if (client == null) {
			synchronized (client) {
				if (client == null) {
					this.client = openStackConnector.connect();
				}
			}
		}
		return client;
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
