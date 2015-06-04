package cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.settings;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("SimulationSettings")
public class SimulationSettings {
	@XStreamAlias("SimulationEnd")
	private final SimulationEndSettings simulationEndSettings;

	@XStreamAlias("Asserts")
	private final AssertsSettings assertsSettings;
	
	@XStreamAlias("Statistics")
	private final StatisticsSettings statisticsSettings;

	public SimulationSettings(SimulationEndSettings simulationEndSettings, AssertsSettings assertsSettings,
			StatisticsSettings statisticsSettings) {
		this.simulationEndSettings = simulationEndSettings;
		this.assertsSettings = assertsSettings;
		this.statisticsSettings = statisticsSettings;
	}

	public SimulationEndSettings getSimulationEndSettings() {
		return simulationEndSettings;
	}

	public AssertsSettings getAssertsSettings() {
		return assertsSettings;
	}

	public StatisticsSettings getStatisticsSettings() {
		return statisticsSettings;
	}
}
