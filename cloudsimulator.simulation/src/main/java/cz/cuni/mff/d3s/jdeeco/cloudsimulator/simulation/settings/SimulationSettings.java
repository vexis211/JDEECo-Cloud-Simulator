package cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.settings;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("SimulationSettings")
public class SimulationSettings {
	@XStreamAlias("SimulationRuns")
	private final SimulationRunSettings simulationRunSettings;

	@XStreamAlias("Asserts")
	private final AssertsSettings assertsSettings;
	
	@XStreamAlias("Statistics")
	private final StatisticsSettings statisticsSettings;

	public SimulationSettings(SimulationRunSettings simulationRunSettings, AssertsSettings assertsSettings,
			StatisticsSettings statisticsSettings) {
		this.simulationRunSettings = simulationRunSettings;
		this.assertsSettings = assertsSettings;
		this.statisticsSettings = statisticsSettings;
	}

	public SimulationRunSettings getSimulationRunSettings() {
		return simulationRunSettings;
	}

	public AssertsSettings getAssertsSettings() {
		return assertsSettings;
	}

	public StatisticsSettings getStatisticsSettings() {
		return statisticsSettings;
	}
}
