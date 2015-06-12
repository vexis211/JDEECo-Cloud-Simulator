package cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.settings;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("DefaultAssert")
public class DefaultAssertSetting {

	@XStreamAlias("Actions")
	@XStreamAsAttribute
	private final String actions;

	public DefaultAssertSetting(String actions) {
		this.actions = actions;
	}

	public String getActions() {
		return actions;
	}
}
