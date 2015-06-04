package cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.settings;

public class XmlSettingsProviderImpl implements SimulationSettingsProvider, AssertsSettingsProvider,
		StatisticsSettingsProvider {

	private final String settingsFileName;
	private SimulationSettings simulationSettings;

	public XmlSettingsProviderImpl(String settingsFileName) {
		this.settingsFileName = settingsFileName;
	}

	@Override
	public SimulationSettings getSimulationSettings() {
		LoadSimulationSettings();

		return simulationSettings;
	}

	@Override
	public AssertsSettings getAssertsSettings() {
		LoadSimulationSettings();
		
		return simulationSettings.getAssertsSettings();
	}

	@Override
	public StatisticsSettings getStatisticsSettings() {
		LoadSimulationSettings();

		return simulationSettings.getStatisticsSettings();
	}

	private void LoadSimulationSettings() {
		if (simulationSettings == null) {
			SettingsLoader settingsLoader = new SettingsLoader();
			simulationSettings = settingsLoader.load(settingsFileName);
		}
	}
}
