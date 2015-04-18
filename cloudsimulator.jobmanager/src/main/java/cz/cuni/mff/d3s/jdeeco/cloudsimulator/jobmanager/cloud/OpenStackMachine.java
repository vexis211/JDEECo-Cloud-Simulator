package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.cloud;

import org.openstack4j.model.compute.Server;
import org.openstack4j.model.compute.Server.Status;

public class OpenStackMachine implements CloudMachine {

	private final Server server;
	private CloudMachineStatus status;

	public OpenStackMachine(Server server) {
		this.server = server;

		Status serverStatus = server.getStatus();
		switch (serverStatus) {
		case ACTIVE:
			this.status = CloudMachineStatus.Started;
			break;
		default:
			this.status = CloudMachineStatus.Stopped;
			break;
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
