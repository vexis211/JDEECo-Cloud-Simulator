package cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web.client.data;

public class NavigationPathStepImpl implements NavigationPathStep {

	private String name;
	private String url;

	public NavigationPathStepImpl(String name, String url) {
		this.name = name;
		this.url = url;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getUrl() {
		return url;
	}
}
