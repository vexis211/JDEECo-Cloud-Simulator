package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.cloud;

public class CloudMachineBuilderParamsImpl implements CloudMachineBuilderParams {

	private final String templateName;
	private final String flavorName;
	private final String networkName;

	public CloudMachineBuilderParamsImpl(String templateName, String flavorName, String networkName) {
		this.templateName = templateName;
		this.flavorName = flavorName;
		this.networkName = networkName;
	}

	@Override
	public String getTemplateName() {
		return templateName;
	}

	@Override
	public String getFlavorName() {
		return flavorName;
	}

	@Override
	public String getNetworkName() {
		return networkName;
	}
}
