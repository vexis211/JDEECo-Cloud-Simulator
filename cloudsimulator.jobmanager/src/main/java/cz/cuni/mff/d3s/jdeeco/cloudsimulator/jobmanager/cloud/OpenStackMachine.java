package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.cloud;

import org.openstack4j.model.compute.Server;

public class OpenStackMachine implements CloudMachine {

	private final Server server;
	
	public OpenStackMachine(Server server) {
		this.server = server;
	}
	
	@Override
	public String getId() {
		return server.getId();
	}

	@Override
	public String getName() {
		return server.getName();
	}

}
