package cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.cloud;

import org.openstack4j.api.OSClient;

public class OpenStackComponent {

	private final Object locker = new Object();
	protected final OpenStackConnector openStackConnector;
	
	private OSClient client;

	public OpenStackComponent(OpenStackConnector openStackConnector) {
		this.openStackConnector = openStackConnector;
	}

	protected OSClient getClient() {
		if (client == null) {
			synchronized (locker) {
				if (client == null) {
					this.client = openStackConnector.connect();
				}
			}
		}
		return client;
	}
}
