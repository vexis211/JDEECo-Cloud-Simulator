package cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.settings;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("DefaultStatistic")
public class DefaultStatisticSetting {

	@XStreamAlias("Save")
	@XStreamAsAttribute
	private final String save;

	public String getSave() {
		return save;
	}

	public DefaultStatisticSetting(String save) {
		this.save = save;
	}
}
