package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.cloud;

import org.openstack4j.model.compute.Server;

public interface OpenStackMachineService extends CloudMachineService {

	OpenStackMachine registerCreatedMachine(Server newServer);
}
