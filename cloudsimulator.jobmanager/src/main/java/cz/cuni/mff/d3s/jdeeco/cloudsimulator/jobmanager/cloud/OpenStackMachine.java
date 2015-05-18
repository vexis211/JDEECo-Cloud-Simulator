package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.cloud;

import org.openstack4j.model.compute.Server;
import org.openstack4j.model.compute.Server.Status;

public class OpenStackMachine implements CloudMachine {

	private final Server server;
	private final CloudMachineStatus status;

	public OpenStackMachine(Server server) {
		this.server = server;
		this.status = getStatusFrom(server);
	}

	private CloudMachineStatus getStatusFrom(Server server) {

		if (server.getStatus() == null) {
			return CloudMachineStatus.Starting;
		}

		Status serverStatus = server.getStatus();
		switch (serverStatus) {
		case ACTIVE:
			return CloudMachineStatus.Started;
		default:
			return CloudMachineStatus.Stopped;
		}
	}

	@Override
	public String getId() {
		return server.getId();
	}

	@Override
	public String getName() {
		return server.getName();
	}

	@Override
	public CloudMachineStatus getStatus() {
		return status;
	}
}
