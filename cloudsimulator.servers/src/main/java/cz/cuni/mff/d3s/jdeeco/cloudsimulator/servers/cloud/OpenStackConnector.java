package cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.cloud;

import org.openstack4j.api.OSClient;

public interface OpenStackConnector {
	OSClient connect();
}
