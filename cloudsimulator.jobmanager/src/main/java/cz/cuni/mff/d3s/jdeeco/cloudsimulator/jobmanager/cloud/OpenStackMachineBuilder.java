package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.cloud;

import org.apache.log4j.Logger;
import org.openstack4j.api.Builders;
import org.openstack4j.model.compute.Flavor;
import org.openstack4j.model.compute.Image;
import org.openstack4j.model.compute.Server;
import org.openstack4j.model.compute.ServerCreate;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.cloud.OpenStackComponent;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.cloud.OpenStackConnector;

public class OpenStackMachineBuilder extends OpenStackComponent implements CloudMachineBuilder {

	private final Logger logger = Logger.getLogger(OpenStackMachineBuilder.class);
	
	private final OpenStackMachineService openStackMachineService;

	private final String templateName;
	private final String flavorName;
	private final String machineName;

	private String flavorId;
	private String imageId;


	public OpenStackMachineBuilder(OpenStackConnector openStackConnector,
			OpenStackMachineService openStackMachineService, String templateName, String flavorName, String machineName) {
		super(openStackConnector);
		this.openStackMachineService = openStackMachineService;

		this.templateName = templateName;
		this.flavorName = flavorName;
		this.machineName = machineName;
	}

	private String getFlavorId() {
		if (this.flavorId == null) {
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
		logger.info("Creating new OpenStack server...");
		
		// create a server model object
		ServerCreate newServerCreate = Builders.server().name(machineName).flavor(getFlavorId()).image(getImageId())
				.build();

		// boot the server
		Server newServer = getClient().compute().servers().boot(newServerCreate);

		OpenStackMachine machine = openStackMachineService.registerCreatedMachine(newServer);
		return machine;
	}
}
