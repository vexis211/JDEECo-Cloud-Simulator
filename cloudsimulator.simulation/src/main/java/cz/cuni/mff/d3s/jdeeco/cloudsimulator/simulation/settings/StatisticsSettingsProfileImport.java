package cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.settings;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("Import")
public class StatisticsSettingsProfileImport {

	@XStreamAlias("ProfileId")
	@XStreamAsAttribute
	private final String profileId;

	public String getProfileId() {
		return profileId;
	}

	public StatisticsSettingsProfileImport(String profileId) {
		this.profileId = profileId;
	}
}
