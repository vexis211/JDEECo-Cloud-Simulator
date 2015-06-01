package cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.settings;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("Statistic")
public class StatisticSetting {

	@XStreamAlias("Type")
	@XStreamAsAttribute
	private final StatisticSettingType type;
	
	@XStreamAlias("Id")
	@XStreamAsAttribute
	private final String id;

	@XStreamAlias("Save")
	@XStreamAsAttribute
	private final String save;

	public StatisticSetting(StatisticSettingType type, String id, String save) {
		this.type = type;
		this.id = id;
		this.save = save;
	}

	public StatisticSettingType getType() {
		return type;
	}

	public String getId() {
		return id;
	}

	public String getSave() {
		return save;
	}
}
