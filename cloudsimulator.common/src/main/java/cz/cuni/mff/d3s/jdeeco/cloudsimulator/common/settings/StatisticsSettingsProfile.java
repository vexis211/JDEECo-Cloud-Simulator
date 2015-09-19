package cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.settings;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.settings.DefaultStatisticSetting;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.settings.SettingsProfileImport;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.settings.StatisticSetting;

@XStreamAlias("StatisticProfile")
public class StatisticsSettingsProfile {

	@XStreamAsAttribute
	@XStreamAlias("Id")
	private final String id;

	@XStreamAlias("DefaultStatistic")
	private final DefaultStatisticSetting defaultStatisticSetting;

	@XStreamImplicit(itemFieldName = "Import")
	private final ArrayList<SettingsProfileImport> profileImports;

	@XStreamImplicit(itemFieldName = "Statistic")
	private final ArrayList<StatisticSetting> statisticSettings;
	
	public StatisticsSettingsProfile(String id, DefaultStatisticSetting defaultStatisticSetting,
			List<SettingsProfileImport> profileImports, List<StatisticSetting> statisticSettings) {
		this.id = id;
		this.defaultStatisticSetting = defaultStatisticSetting;
		this.profileImports = profileImports != null ? new ArrayList<SettingsProfileImport>(profileImports) : null;
		this.statisticSettings = statisticSettings != null ? new ArrayList<StatisticSetting>(statisticSettings) : null;
	}

	public String getId() {
		return id;
	}

	public DefaultStatisticSetting getDefaultStatisticSettings() {
		return defaultStatisticSetting;
	}

	public List<SettingsProfileImport> getProfileImports() {
		return profileImports;
	}

	public List<StatisticSetting> getStatisticSettings() {
		return statisticSettings;
	}
}
