package cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.asserts;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.data.AssertAction;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.settings.AssertGroupSetting;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.settings.AssertSettingsProfile;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.settings.AssertsSettings;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.settings.AssertsSettingsProvider;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.settings.DefaultAssertSetting;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.settings.SettingsProfileImport;

public class AssertConfigurationImpl implements AssertConfiguration {

	private static final Logger logger = LoggerFactory.getLogger(AssertConfigurationImpl.class);

	private final HashMap<String, EnumSet<AssertAction>> exactDefinitions = new HashMap<String, EnumSet<AssertAction>>();
	private EnumSet<AssertAction> defaultAssertActions;

	private final AssertsSettings settings;
	private final String assertsProfileId;

	public AssertConfigurationImpl(AssertsSettingsProvider settingsProvider, String assertsProfileId) {
		this.settings = settingsProvider.getAssertsSettings();
		this.assertsProfileId = assertsProfileId;

		this.initializeSettings();
	}

	private final void initializeSettings() {
		Optional<AssertSettingsProfile> profileOptional = this.settings.getProfiles().stream()
				.filter(x -> x.getId().equals(this.assertsProfileId)).findAny();

		if (profileOptional.isPresent()) {
			initializeSettingsProfile(profileOptional.get());
		} else {
			String message = String.format("Asserts profile '%s' is not defined.", this.assertsProfileId);
			logger.error(message);
			throw new RuntimeException(message);
		}
	}

	private final void initializeSettingsProfile(AssertSettingsProfile selectedProfile) {

		Stack<AssertSettingsProfile> profilesToProcess = new Stack<AssertSettingsProfile>();
		Set<AssertSettingsProfile> processedProfiles = new HashSet<AssertSettingsProfile>();
		profilesToProcess.push(selectedProfile);

		Map<String, AssertSettingsProfile> profileId2Profile = this.settings.getProfiles().stream()
				.collect(Collectors.toMap(x -> x.getId(), x -> x));

		// loading profile + imported profiles
		while (!profilesToProcess.isEmpty()) {
			AssertSettingsProfile profile = profilesToProcess.pop();
			if (processedProfiles.contains(profile)) {
				String message = String
						.format("Error occurred while processing assert settings. There is an circular dependency of imports. Profile '%s' is in this circle.",
								profile.getId());
				logger.error(message);
				throw new RuntimeException(message);
			}
			processedProfiles.add(profile);

			applyDefaultFromProfile(profile);
			applyAssertsFromProfile(profile);

			// we want to let later added imports to have preference between previously added imports
			List<SettingsProfileImport> profileImports = profile.getProfileImports();
			for (int i = profileImports.size() - 1; i >= 0; i--) {
				String importProfileId = profileImports.get(i).getProfileId();
				if (profileId2Profile.containsKey(importProfileId)) {
					profilesToProcess.push(profileId2Profile.get(importProfileId));
				} else {
					String message = String
							.format("Cannot import assert settings profile with ID: '%s'. There is no such profile. All profile IDs: '%s'.",
									importProfileId, String.join(", ", profileId2Profile.keySet()));
					logger.error(message);
					throw new RuntimeException(message);
				}
			}
		}

		if (defaultAssertActions == null) {
			// default assert from settings is used if it is not set in profiles
			DefaultAssertSetting defaultAssert = settings.getDefaultSetting();
			if (defaultAssert != null) {
				this.defaultAssertActions = AssertActionsParser.parseAssertActions(defaultAssert.getActions());
			} else {
				String message = "Default assert settings cannot be null.";
				logger.error(message);
				throw new RuntimeException(message);
			}
		}
	}

	private void applyDefaultFromProfile(AssertSettingsProfile profile) {
		if (this.defaultAssertActions == null && profile.getDefaultAssertSettings() != null) {
			String saveModesString = profile.getDefaultAssertSettings().getActions();
			this.defaultAssertActions = AssertActionsParser.parseAssertActions(saveModesString);
		}
	}

	private final void applyAssertsFromProfile(AssertSettingsProfile profile) {

		for (AssertGroupSetting assertGroupSetting : profile.getAssertGroupSettings()) {
			String toMatch = assertGroupSetting.getId();
			EnumSet<AssertAction> assertActions = AssertActionsParser.parseAssertActions(assertGroupSetting
					.getActions());

			if (!exactDefinitions.containsKey(toMatch)) {
				exactDefinitions.put(toMatch, assertActions);
			}
		}
	}

	public final EnumSet<AssertAction> getDefaultSaveModes(AssertSettingsProfile profile) {
		DefaultAssertSetting assertDefaultSetting = profile.getDefaultAssertSettings() != null ? profile
				.getDefaultAssertSettings() : this.settings.getDefaultSetting();
		return AssertActionsParser.parseAssertActions(assertDefaultSetting.getActions());
	}

	@Override
	public EnumSet<AssertAction> getAssertActions(String groupId) {
		if (exactDefinitions.containsKey(groupId)) {
			return exactDefinitions.get(groupId);
		}

		return defaultAssertActions;
	}
}
