package cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.settings;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.thoughtworks.xstream.XStream;

public class SettingsLoader {

	private static Logger logger = LogManager.getLogger(SettingsLoader.class);
	
	private XStream xStream;

	public SettingsLoader() {
		this.xStream = new XStream();
		this.xStream.processAnnotations(SimulationSettings.class);
		this.xStream.autodetectAnnotations(true);
	}

	public SimulationSettings load(String fileName) {
		
		try (InputStream input = new FileInputStream(fileName)) {
			SimulationSettings newSettings = (SimulationSettings) xStream.fromXML(input);
			return newSettings;
		} catch (IOException e) {
			logger.error("Cannot load simulation settings from file: " + fileName);
			return null;
		}
	}
}
