package cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.settings;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("AssertGroup")
public class AssertGroupSetting {

	@XStreamAlias("Id")
	@XStreamAsAttribute
	private final String id;

	@XStreamAlias("Actions")
	@XStreamAsAttribute
	private final String actions;

	public AssertGroupSetting(String id, String actions) {
		this.id = id;
		this.actions = actions;
	}

	public String getId() {
		return id;
	}

	public String getActions() {
		return actions;
	}
}
