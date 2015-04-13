package cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.cloud;

import org.openstack4j.api.OSClient;
import org.openstack4j.openstack.OSFactory;

public class OpenStackConnectorImpl implements OpenStackConnector {

	private final String endpoint;
	private final String user;
	private final String password;
	private final String tenantName;

	public OpenStackConnectorImpl(String endpoint, String user, String password, String tenantName) {
		this.endpoint = endpoint;
		this.user = user;
		this.password = password;
		this.tenantName = tenantName;
	}

	public OSClient connect() {
		OSClient os = OSFactory.builder().endpoint(endpoint).credentials(user, password).tenantName(tenantName)
				.authenticate();
		return os;
	}
}	
