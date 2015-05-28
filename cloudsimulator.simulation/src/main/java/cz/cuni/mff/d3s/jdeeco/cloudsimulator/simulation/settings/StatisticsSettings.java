package cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.settings;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("Statistics")
public class StatisticsSettings {

	@XStreamAlias("Default")
	private final DefaultStatisticSetting defaultStatistic;

	@XStreamAlias("Profiles")
	private final ArrayList<StatisticsSettingsProfile> profiles;

	public DefaultStatisticSetting getDefaultStatistic() {
		return defaultStatistic;
	}

	public List<StatisticsSettingsProfile> getProfiles() {
		return profiles;
	}

	public StatisticsSettings(DefaultStatisticSetting defaultStatistic, List<StatisticsSettingsProfile> profiles) {
		this.defaultStatistic = defaultStatistic;
		this.profiles = profiles != null ? new ArrayList<StatisticsSettingsProfile>(profiles) : null;
	}

	private Object readResolve() throws IOException {
		if (this.defaultStatistic == null) {
			throw new IOException("Cannot read object: Default element is compulsory.");
		}
		return this;
	}
}
