package cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.settings;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("Statistics")
public class StatisticsSettings {

	@XStreamAlias("DefaultStatistic")
	private final DefaultStatisticSetting defaultSetting;

	@XStreamAlias("StatisticProfiles")
	private final ArrayList<StatisticsSettingsProfile> profiles;

	public DefaultStatisticSetting getDefaultStatistic() {
		return defaultSetting;
	}

	public List<StatisticsSettingsProfile> getProfiles() {
		return profiles;
	}

	public StatisticsSettings(DefaultStatisticSetting defaultSetting, List<StatisticsSettingsProfile> profiles) {
		this.defaultSetting = defaultSetting;
		this.profiles = profiles != null ? new ArrayList<StatisticsSettingsProfile>(profiles) : null;
	}

	private Object readResolve() throws IOException {
		if (this.defaultSetting == null) {
			throw new IOException("Cannot read object: Default element is compulsory.");
		}
		return this;
	}
}
