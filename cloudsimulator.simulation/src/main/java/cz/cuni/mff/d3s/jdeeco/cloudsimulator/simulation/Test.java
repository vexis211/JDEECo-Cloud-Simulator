package cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.thoughtworks.xstream.XStream;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.settings.AssertGroupSetting;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.settings.AssertSettingsProfile;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.settings.AssertsSettings;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.settings.DefaultAssertSetting;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.settings.DefaultStatisticSetting;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.settings.ListVariableSetting;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.settings.RangeVariableSetting;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.settings.SettingsProfileImport;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.settings.SimulationEndSettings;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.settings.SimulationRunSettings;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.settings.SimulationRunSettingsProfile;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.settings.SimulationSettings;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.settings.StatisticSetting;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.settings.StatisticsSettings;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.settings.StatisticsSettingsProfile;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.settings.VariableSetting;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.settings.VariablesSettings;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.data.AssertAction;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.data.SimulationEndSpecificationType;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.data.StatisticSettingType;

public class Test {
	public static void main(String[] args) throws IOException {
		testXml();
	}

	private static void testXml() {

		// run
		SimulationEndSettings simulationEndSettings = new SimulationEndSettings(SimulationEndSpecificationType.RealTime,
				10 * 60 * 1000);

		VariablesSettings variablesSettings = new VariablesSettings(new ArrayList<VariableSetting>(
				Arrays.asList(new ListVariableSetting("ListVar1", int.class.getSimpleName(), "1,5,9"),
						new RangeVariableSetting("RangeVar1", String.class.getSimpleName(), "7", "15", "2"))));

		SimulationRunSettings simulationRunSettings = new SimulationRunSettings(
				Arrays.asList(new SimulationRunSettingsProfile("default", simulationEndSettings, variablesSettings)));

		// asserts
		DefaultAssertSetting defaultAssertSetting = new DefaultAssertSetting(AssertAction.Log.toString());
		List<AssertGroupSetting> assertGroups = Arrays
				.asList(new AssertGroupSetting("Severe", AssertAction.ExitRun.toString()));

		AssertSettingsProfile assertProfile1 = new AssertSettingsProfile("assertProfile1", defaultAssertSetting, null,
				assertGroups);

		AssertsSettings assertsSettings = new AssertsSettings(defaultAssertSetting, Arrays.asList(assertProfile1));

		// statistics
		StatisticsSettingsProfile profile1 = new StatisticsSettingsProfile("profile1",
				new DefaultStatisticSetting("Min"), null,
				Arrays.asList(new StatisticSetting(StatisticSettingType.Plain, "Hit", "Count"),
						new StatisticSetting(StatisticSettingType.Plain, "HulaHula", "Count")));
		StatisticsSettingsProfile profile2 = new StatisticsSettingsProfile("profile2",
				new DefaultStatisticSetting("Min,Max,Avg,Vector"),
				Arrays.asList(new SettingsProfileImport("profile1"), new SettingsProfileImport("profile1")),
				Arrays.asList(new StatisticSetting(StatisticSettingType.Plain, "Test1", "Count"),
						new StatisticSetting(StatisticSettingType.Regex, "Test2", "Vector")));

		StatisticsSettings statisticsSettings = new StatisticsSettings(new DefaultStatisticSetting("Min,Max"),
				Arrays.asList(profile1, profile2));

		SimulationSettings settings = new SimulationSettings(simulationRunSettings, assertsSettings,
				statisticsSettings);

		XStream xStream = new XStream();
		xStream.setMode(XStream.NO_REFERENCES);
		xStream.processAnnotations(SimulationSettings.class);
		xStream.autodetectAnnotations(true);

		String file = "SimulationSettings.xml";
		try (OutputStream output = new FileOutputStream(file)) {
			xStream.toXML(settings, output);
			output.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try (InputStream input = new FileInputStream(file)) {
			SimulationSettings newSettings = (SimulationSettings) xStream.fromXML(input);

			System.out.println(newSettings);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// System.in.read();
	}
}
