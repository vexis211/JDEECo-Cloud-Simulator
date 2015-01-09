package cz.cuni.mff.d3s.jdeeco.cloudsimulator.worker.data;

import java.io.File;
import java.io.InputStream;

import org.openstack4j.api.OSClient;
import org.openstack4j.model.common.DLPayload;
import org.openstack4j.model.common.payloads.FilePayload;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.openstack.OpenStackConnector;

public class SimulationDataLoaderImpl implements SimulationDataLoader {

	private final OpenStackConnector openStackConnector;
	private OSClient client;
	
	private final String dataContainerName;
	private final String resultsContainerName;
	private final String logsContainerName;

	
	public SimulationDataLoaderImpl(OpenStackConnector openStackConnector, String dataContainerName,
			String resultsContainerName, String logsContainerName) {
		this.openStackConnector = openStackConnector;
		this.dataContainerName = dataContainerName;
		this.resultsContainerName = resultsContainerName;
		this.logsContainerName = logsContainerName;
	}

	private OSClient getClient() {
		if (client == null) {
			this.client = openStackConnector.connect();
		}
		return client;
	}
	
	@Override
	public InputStream download(String source) {
		DLPayload payload = getClient().objectStorage().objects().download(dataContainerName, source);
		return payload.getInputStream();
	}

	@Override
	public void uploadLogs(String sourcePath, String target) {
		upload(sourcePath, target, logsContainerName);
	}

	@Override
	public void uploadResults(String sourcePath, String target) {
		upload(sourcePath, target, resultsContainerName);
	}

	private void upload(String sourcePath, String target, String targetContainter) {
		FilePayload payload = new FilePayload(new File(sourcePath));
		getClient().objectStorage().objects().put(targetContainter, target, payload);
	}
}
