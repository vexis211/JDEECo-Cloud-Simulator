package cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.settings;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.settings.SimulationRunSettingsProfile;

@XStreamAlias("SimulationRuns")
public class SimulationRunSettings {

	@XStreamAlias("SimulationRunProfiles")
	private final ArrayList<SimulationRunSettingsProfile> profiles;

	public SimulationRunSettings(List<SimulationRunSettingsProfile> profiles) {
		this.profiles = profiles != null ? new ArrayList<SimulationRunSettingsProfile>(profiles) : null;
	}

	public List<SimulationRunSettingsProfile> getProfiles() {
		return profiles;
	}
}
