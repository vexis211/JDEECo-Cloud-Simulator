package cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.cloud;

import org.openstack4j.api.OSClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OpenStackComponent {

	private static final Logger logger = LoggerFactory.getLogger(OpenStackComponent.class);

	private final Object locker = new Object();
	protected final OpenStackConnector openStackConnector;
	
	private OSClient client;

	public OpenStackComponent(OpenStackConnector openStackConnector) {
		this.openStackConnector = openStackConnector;
	}

	protected OSClient getNewClient() {
		logger.trace("Getting new OpenStack client.");
		
		return openStackConnector.connect();
	}

	protected OSClient getClient() {
		logger.trace("Getting OpenStack client.");
		
		if (client == null) {
			synchronized (locker) {
				if (client == null) {
					this.client = getNewClient(); // TODO check - is it OK to connect in every component?
				}
			}
		}
		return client;
	}
}
