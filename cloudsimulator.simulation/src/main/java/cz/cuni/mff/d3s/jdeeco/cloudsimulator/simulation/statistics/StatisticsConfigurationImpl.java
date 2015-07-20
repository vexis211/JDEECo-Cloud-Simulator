package cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.statistics;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.Stack;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.data.StatisticsSaveMode;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.data.StatisticsSaveModesParser;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.settings.DefaultStatisticSetting;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.settings.StatisticSetting;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.settings.StatisticsSettings;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.settings.StatisticsSettingsProfile;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.settings.SettingsProfileImport;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.settings.StatisticsSettingsProvider;

public class StatisticsConfigurationImpl implements StatisticsConfiguration {

	private static final Logger logger = LoggerFactory.getLogger(StatisticsConfigurationImpl.class);

	private final HashMap<String, EnumSet<StatisticsSaveMode>> exactDefinitions = new HashMap<String, EnumSet<StatisticsSaveMode>>();
	private final HashMap<String, EnumSet<StatisticsSaveMode>> patternDefinitions = new HashMap<String, EnumSet<StatisticsSaveMode>>();
	private EnumSet<StatisticsSaveMode> defaultSaveModes;

	private final StatisticsSettings settings;
	private final String statisticsProfileId;

	public StatisticsConfigurationImpl(StatisticsSettingsProvider settingsProvider, String statisticsProfileId) {
		this.settings = settingsProvider.getStatisticsSettings();
		this.statisticsProfileId = statisticsProfileId;

		this.initializeSettings();
	}

	private final void initializeSettings() {
		Optional<StatisticsSettingsProfile> profileOptional = this.settings.getProfiles().stream()
				.filter(x -> x.getId().equals(this.statisticsProfileId)).findAny();

		if (profileOptional.isPresent()) {
			initializeSettingsProfile(profileOptional.get());
		} else {
			String message = String.format("Statistics profile '%s' is not defined.", this.statisticsProfileId);
			logger.error(message);
			throw new RuntimeException(message);
		}
	}

	private final void initializeSettingsProfile(StatisticsSettingsProfile selectedProfile) {

		Stack<StatisticsSettingsProfile> profilesToProcess = new Stack<StatisticsSettingsProfile>();
		Set<StatisticsSettingsProfile> processedProfiles = new HashSet<StatisticsSettingsProfile>();
		profilesToProcess.push(selectedProfile);

		Map<String, StatisticsSettingsProfile> profileId2Profile = this.settings.getProfiles().stream()
				.collect(Collectors.toMap(x -> x.getId(), x -> x));

		// loading profile + imported profiles
		while (!profilesToProcess.isEmpty()) {
			StatisticsSettingsProfile profile = profilesToProcess.pop();
			if (processedProfiles.contains(profile)) {
				String message = String
						.format("Error occurred while processing statistics settings. There is an circular dependency of imports. Profile '%s' is in this circle.",
								profile.getId());
				logger.error(message);
				throw new RuntimeException(message);
			}
			processedProfiles.add(profile);

			applyDefaultFromProfile(profile);
			applyStatisticsFromProfile(profile);

			// we want to let later added imports to have preference between previously added imports
			List<SettingsProfileImport> profileImports = profile.getProfileImports();
			for (int i = profileImports.size() - 1; i >= 0; i--) {
				String importProfileId = profileImports.get(i).getProfileId();
				if (profileId2Profile.containsKey(importProfileId)) {
					profilesToProcess.push(profileId2Profile.get(importProfileId));
				} else {
					String message = String
							.format("Cannot import statistics settings profile with ID: '%s'. There is no such profile. All profile IDs: '%s'.",
									importProfileId, String.join(", ", profileId2Profile.keySet()));
					logger.error(message);
					throw new RuntimeException(message);
				}
			}
		}

		if (defaultSaveModes == null) {
			// default statistic from settings is used if it is not set in profiles
			DefaultStatisticSetting defaultStatistic = settings.getDefaultStatistic();
			if (defaultStatistic != null) {
				this.defaultSaveModes = StatisticsSaveModesParser.parseSaveModes(defaultStatistic.getSave());
			} else {
				String message = "Default statistic in statistic settings cannot be null.";
				logger.error(message);
				throw new RuntimeException(message);
			}
		}
	}

	private final void applyDefaultFromProfile(StatisticsSettingsProfile profile) {
		if (this.defaultSaveModes == null && profile.getDefaultStatisticSettings() != null) {
			String saveModesString = profile.getDefaultStatisticSettings().getSave();
			this.defaultSaveModes = StatisticsSaveModesParser.parseSaveModes(saveModesString);
		}
	}

	private final void applyStatisticsFromProfile(StatisticsSettingsProfile profile) {

		for (StatisticSetting statisticSetting : profile.getStatisticSettings()) {
			String toMatch = statisticSetting.getId();
			EnumSet<StatisticsSaveMode> saveMode = StatisticsSaveModesParser.parseSaveModes(statisticSetting.getSave());

			switch (statisticSetting.getType()) {
			case Plain:
				if (!exactDefinitions.containsKey(toMatch)) {
					exactDefinitions.put(toMatch, saveMode);
				}
				break;
			case Regex:
				if (!patternDefinitions.containsKey(toMatch)) {
					patternDefinitions.put(toMatch, saveMode);
				}
				break;

			default:
				break;
			}
		}
	}

	
	@Override
	public EnumSet<StatisticsSaveMode> getSaveModes(String statisticId) {
		if (exactDefinitions.containsKey(statisticId)) {
			return exactDefinitions.get(statisticId);
		}
		
		for (Entry<String, EnumSet<StatisticsSaveMode>> patternEntry : patternDefinitions.entrySet()) {
			if (Pattern.matches(patternEntry.getKey(), statisticId)) {
				return patternEntry.getValue();
			}
		}
		
		return defaultSaveModes;
	}
}
