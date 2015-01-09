package cz.cuni.mff.d3s.jdeeco.cloudsimulator.worker.data;

import java.io.File;
import java.io.InputStream;

import org.openstack4j.api.OSClient;
import org.openstack4j.model.common.DLPayload;
import org.openstack4j.model.common.payloads.FilePayload;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.openstack.OpenStackConnector;

public class SimulationDataLoaderImpl implements SimulationDataLoader {
	
	private final OSClient client;
	private final String dataContainerName;
	private final String resultsContainerName;

	public SimulationDataLoaderImpl(OpenStackConnector connector, String dataContainerName, String resultsContainerName) {
		this.dataContainerName = dataContainerName;
		this.resultsContainerName = resultsContainerName;
		
		this.client = connector.connect();
	}
	
	@Override
	public InputStream download(String source) {
		DLPayload payload = client.objectStorage().objects().download(dataContainerName, source);
		return payload.getInputStream();
	}

	@Override
	public void upload(String sourceUri, String target) {
		FilePayload payload = new FilePayload(new File(sourceUri));
		client.objectStorage().objects().put(resultsContainerName, target, payload);
	}
}
