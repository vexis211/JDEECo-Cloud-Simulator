package cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.settings;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.settings.AssertsSettings;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.settings.AssertsSettingsProvider;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.settings.SettingsLoader;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.settings.SimulationRunSettings;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.settings.SimulationRunSettingsProvider;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.settings.SimulationSettings;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.settings.SimulationSettingsProvider;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.settings.StatisticsSettings;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.settings.StatisticsSettingsProvider;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.settings.XmlSettingsProviderImpl;

public class XmlSettingsProviderImpl implements SimulationSettingsProvider, AssertsSettingsProvider,
		StatisticsSettingsProvider, SimulationRunSettingsProvider {

	private static Logger logger = LoggerFactory.getLogger(XmlSettingsProviderImpl.class);

	private final String settingsFileName;
	private SimulationSettings simulationSettings;

	public XmlSettingsProviderImpl(String settingsFileName) {
		this.settingsFileName = settingsFileName;
	}

	@Override
	public SimulationSettings getSimulationSettings() {
		logger.debug("Getting simulation settings...");

		if (simulationSettings == null) {
			logger.info("Loading simulation settings from file: '{}'...", settingsFileName);

			SettingsLoader settingsLoader = new SettingsLoader();
			simulationSettings = settingsLoader.load(settingsFileName);

			logger.info("Simulation settings successfully loaded from file: '{}'.", settingsFileName);
		}
		return simulationSettings;
	}

	@Override
	public AssertsSettings getAssertsSettings() {
		logger.debug("Getting simulation assert settings...");

		return getSimulationSettings().getAssertsSettings();
	}

	@Override
	public StatisticsSettings getStatisticsSettings() {
		logger.debug("Getting simulation statistic settings...");

		return getSimulationSettings().getStatisticsSettings();
	}

	@Override
	public SimulationRunSettings getSimulationRunSettings() {
		logger.debug("Getting simulation run settings...");

		return getSimulationSettings().getSimulationRunSettings();
	}
}
