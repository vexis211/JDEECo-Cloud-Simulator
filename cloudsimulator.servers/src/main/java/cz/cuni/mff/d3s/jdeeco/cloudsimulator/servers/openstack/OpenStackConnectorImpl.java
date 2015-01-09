package cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.openstack;

import org.openstack4j.api.OSClient;
import org.openstack4j.openstack.OSFactory;

public class OpenStackConnectorImpl implements OpenStackConnector {
	public OSClient connect() {
		OSClient os = OSFactory.builderV3().endpoint("http://127.0.0.1:5000/v3").credentials("admin", "sample")
				.domainName("example-domain").authenticate();
		// TODO
		return os;
	}
}
