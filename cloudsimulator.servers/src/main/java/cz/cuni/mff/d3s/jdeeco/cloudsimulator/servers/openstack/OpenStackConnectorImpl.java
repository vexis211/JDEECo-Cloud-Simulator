package cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.openstack;

import org.openstack4j.api.OSClient;
import org.openstack4j.openstack.OSFactory;

public class OpenStackConnectorImpl implements OpenStackConnector {

	private final String endpoint;
	private final String user;
	private final String password;
	private final String domainName;

	public OpenStackConnectorImpl(String endpoint, String user, String password, String domainName) {
		this.endpoint = endpoint;
		this.user = user;
		this.password = password;
		this.domainName = domainName;
	}

	public OSClient connect() {
		OSClient os = OSFactory.builderV3().endpoint(endpoint).credentials(user, password).domainName(domainName)
				.authenticate();
		return os;
	}
}
