package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.cloud;

import org.openstack4j.api.Builders;
import org.openstack4j.model.compute.Flavor;
import org.openstack4j.model.compute.Image;
import org.openstack4j.model.compute.Server;
import org.openstack4j.model.compute.ServerCreate;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.cloud.OpenStackComponent;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.cloud.OpenStackConnector;

public class OpenStackMachineBuilder extends OpenStackComponent implements CloudMachineBuilder {

	private final OpenStackMachineService openStackMachineService;

	private final String templateName;
	private final String machineName;

	private String flavorId;
	private String imageId;

	public OpenStackMachineBuilder(OpenStackConnector openStackConnector,
			OpenStackMachineService openStackMachineService, String templateName, String machineName) {
		super(openStackConnector);
		this.openStackMachineService = openStackMachineService;

		this.templateName = templateName;
		this.machineName = machineName;
	}

	private String getFlavorId() {
		if (this.flavorId == null) {
			String flavorName = OpenStackInfrastructureInitializerParameters.WORKER_FLAVOR_NAME; // TODO do better!
			Flavor flavor = getClient().compute().flavors().list().stream().filter(x -> x.getName().equals(flavorName))
					.findFirst().get();
			this.flavorId = flavor.getId();
		}
		return this.flavorId;
	}

	private String getImageId() {
		if (this.imageId == null) {
			Image image = getClient().compute().images().list().stream().filter(x -> x.getName().equals(templateName))
					.findFirst().get();
			this.imageId = image.getId();
		}
		return this.imageId;
	}

	@Override
	public CloudMachine build() {

		// create a server model object
		ServerCreate newServerCreate = Builders.server().name(machineName).flavor(getFlavorId()).image(getImageId())
				.build();

		// boot the server
		Server newServer = getClient().compute().servers().boot(newServerCreate);

		OpenStackMachine machine = openStackMachineService.registerCreatedMachine(newServer);
		return machine;
	}
}
