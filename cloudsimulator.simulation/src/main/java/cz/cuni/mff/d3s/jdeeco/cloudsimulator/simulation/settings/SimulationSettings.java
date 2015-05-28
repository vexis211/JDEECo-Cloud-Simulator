package cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.settings;


import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("SimulationSettings")
public class SimulationSettings {
	@XStreamAlias("Statistics")
	private final StatisticsSettings statisticsSettings;

	public StatisticsSettings getStatisticsSettings() {
		return statisticsSettings;
	}
	
	public SimulationSettings(StatisticsSettings statisticsSettings) {
		this.statisticsSettings = statisticsSettings;
	}
}
