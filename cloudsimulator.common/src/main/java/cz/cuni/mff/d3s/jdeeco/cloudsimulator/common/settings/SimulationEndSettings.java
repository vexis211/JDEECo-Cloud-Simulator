package cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.settings;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.data.SimulationEndSpecificationType;

@XStreamAlias("SimulationEnd")
public class SimulationEndSettings {
	@XStreamAsAttribute
	@XStreamAlias("Type")
	private final SimulationEndSpecificationType type;

	/**
	 * This is real time in milliseconds if type == RealTime or simulation time in units of simulation time if type ==
	 * SimulationTime
	 */
	@XStreamAsAttribute
	@XStreamAlias("Time")
	private final long time;

	public SimulationEndSettings(SimulationEndSpecificationType type, long time) {
		this.type = type;
		this.time = time;
	}

	public SimulationEndSpecificationType getType() {
		return type;
	}

	public long getTime() {
		return time;
	}
}
