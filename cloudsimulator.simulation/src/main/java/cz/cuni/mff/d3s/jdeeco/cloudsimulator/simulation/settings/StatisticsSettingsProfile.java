package cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.settings;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("Profile")
public class StatisticsSettingsProfile {

	@XStreamAsAttribute
	@XStreamAlias("Id")
	private final String id;

	@XStreamAlias("Default")
	private final DefaultStatisticSetting defaultStatistic;

	@XStreamImplicit(itemFieldName = "Import")
	private final ArrayList<StatisticsSettingsProfileImport> imports;

	@XStreamImplicit(itemFieldName = "Statistic")
	private final ArrayList<StatisticSetting> statistics;
	
	public StatisticsSettingsProfile(String id, DefaultStatisticSetting defaultStatistic,
			List<StatisticsSettingsProfileImport> imports, List<StatisticSetting> statistics) {
		this.id = id;
		this.defaultStatistic = defaultStatistic;
		this.imports = imports != null ? new ArrayList<StatisticsSettingsProfileImport>(imports) : null;
		this.statistics = statistics != null ? new ArrayList<StatisticSetting>(statistics) : null;
	}

	public String getId() {
		return id;
	}

	public DefaultStatisticSetting getDefaultStatistic() {
		return defaultStatistic;
	}

	public List<StatisticsSettingsProfileImport> getImports() {
		return imports;
	}

	public List<StatisticSetting> getStatistics() {
		return statistics;
	}
}
