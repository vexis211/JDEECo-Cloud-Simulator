package cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.settings;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("ProfileImport")
public class SettingsProfileImport {

	@XStreamAlias("ProfileId")
	@XStreamAsAttribute
	private final String profileId;

	public String getProfileId() {
		return profileId;
	}

	public SettingsProfileImport(String profileId) {
		this.profileId = profileId;
	}
}
