package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.cloud;

import java.util.Arrays;

import org.apache.log4j.Logger;
import org.openstack4j.api.Builders;
import org.openstack4j.model.compute.Flavor;
import org.openstack4j.model.compute.Image;
import org.openstack4j.model.compute.Server;
import org.openstack4j.model.compute.ServerCreate;
import org.openstack4j.model.network.Network;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.cloud.OpenStackComponent;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.cloud.OpenStackConnector;

public class OpenStackMachineBuilder extends OpenStackComponent implements CloudMachineBuilder {

	private final Logger logger = Logger.getLogger(OpenStackMachineBuilder.class);

	private final OpenStackMachineService openStackMachineService;

	private final String machineName;
	private final CloudMachineBuilderParams builderParams;

	private String flavorId;
	private String imageId;
	private String networkId;

	public OpenStackMachineBuilder(OpenStackConnector openStackConnector,
			OpenStackMachineService openStackMachineService, String machineName, CloudMachineBuilderParams builderParams) {
		super(openStackConnector);
		this.openStackMachineService = openStackMachineService;

		this.machineName = machineName;
		this.builderParams = builderParams;
	}

	private String getFlavorId() {
		if (this.flavorId == null) {
			Flavor flavor = getClient().compute().flavors().list().stream()
					.filter(x -> x.getName().equals(builderParams.getFlavorName())).findFirst().get();
			this.flavorId = flavor.getId();
		}
		return this.flavorId;
	}

	private String getImageId() {
		if (this.imageId == null) {
			Image image = getClient().compute().images().list().stream()
					.filter(x -> x.getName().equals(builderParams.getTemplateName())).findFirst().get();
			this.imageId = image.getId();
		}
		return this.imageId;
	}

	private String getNetworkId() {
		if (this.networkId == null) {
			Network network = getClient().networking().network().list().stream()
					.filter(x -> x.getName().equals(builderParams.getNetworkName())).findFirst().get();
			this.networkId = network.getId();
		}
		return this.networkId;
	}

	@Override
	public CloudMachine build() {
		logger.info("Creating new OpenStack server...");

		// create a server model object
		ServerCreate newServerCreate = Builders.server().name(machineName).flavor(getFlavorId()).image(getImageId())
				.networks(Arrays.asList(getNetworkId())).build();

		// boot the server
		Server newServer = getClient().compute().servers().boot(newServerCreate);

		OpenStackMachine machine = openStackMachineService.registerCreatedMachine(newServer);
		return machine;
	}
}
