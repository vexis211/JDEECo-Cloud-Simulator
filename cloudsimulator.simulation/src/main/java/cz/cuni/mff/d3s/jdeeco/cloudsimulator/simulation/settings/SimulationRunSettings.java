package cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.settings;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("SimulationRunSettings")
public class SimulationRunSettings {

	@XStreamAlias("Profiles")
	private final ArrayList<SimulationRunSettingsProfile> profiles;

	public SimulationRunSettings(List<SimulationRunSettingsProfile> profiles) {
		this.profiles = profiles != null ? new ArrayList<SimulationRunSettingsProfile>(profiles) : null;
	}

	public List<SimulationRunSettingsProfile> getProfiles() {
		return profiles;
	}
}
