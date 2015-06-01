package cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

import com.thoughtworks.xstream.XStream;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.settings.DefaultStatisticSetting;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.settings.SimulationSettings;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.settings.StatisticSetting;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.settings.StatisticSettingType;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.settings.StatisticsSettings;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.settings.StatisticsSettingsProfile;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.settings.StatisticsSettingsProfileImport;

public class Test {
	public static void main(String[] args) throws IOException {

		 testXml();
	}

	private static void testXml() {
		StatisticsSettingsProfile profile1 = new StatisticsSettingsProfile("profile1", new DefaultStatisticSetting(
				"Min"), null, Arrays.asList(new StatisticSetting(StatisticSettingType.Plain, "Hit", "Count"), new StatisticSetting(StatisticSettingType.Plain,"HulaHula",
				"Count")));
		StatisticsSettingsProfile profile2 = new StatisticsSettingsProfile("profile2", new DefaultStatisticSetting(
				"Min,Max,Avg,Vector"), Arrays.asList(new StatisticsSettingsProfileImport("profile1"),
				new StatisticsSettingsProfileImport("profile1")), Arrays.asList(new StatisticSetting(StatisticSettingType.Plain,"Test1", "Count"),
				new StatisticSetting(StatisticSettingType.Regex,"Test2", "Vector")));

		StatisticsSettings statisticsSettings = new StatisticsSettings(new DefaultStatisticSetting("Min,Max"),
				Arrays.asList(profile1, profile2));

		SimulationSettings settings = new SimulationSettings(statisticsSettings);

		XStream xStream = new XStream();
		xStream.processAnnotations(SimulationSettings.class);
		xStream.autodetectAnnotations(true);

		String file = "SimulationSettings.xml";
		try (OutputStream output = new FileOutputStream(file)) {
			xStream.toXML(settings, output);
			output.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try (InputStream input = new FileInputStream(file)) {
			SimulationSettings newSettings = (SimulationSettings) xStream.fromXML(input);

			System.out.println(newSettings);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// System.in.read();
	}
}
