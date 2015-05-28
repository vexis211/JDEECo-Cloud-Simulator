package cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.settings;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("Statistic")
public class StatisticSetting {

	@XStreamAlias("Id")
	@XStreamAsAttribute
	private final String id;

	@XStreamAlias("Save")
	@XStreamAsAttribute
	private final String save;

	public String getId() {
		return id;
	}

	public String getSave() {
		return save;
	}

	public StatisticSetting(String id, String save) {
		this.id = id;
		this.save = save;
	}
}
