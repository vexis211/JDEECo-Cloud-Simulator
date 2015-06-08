package cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.startup;

import java.io.File;
import java.io.FileNotFoundException;

import org.apache.log4j.Logger;
import org.springframework.util.Log4jConfigurer;

public class LogInitializer {

	public static void initialize() throws FileNotFoundException {
		File logsDir = new File("logs"); // TODO improvement - from settings
		logsDir.mkdirs();

		// configure logger from XML configuration file
		Log4jConfigurer.initLogging("classpath:configuration/log4j.xml");
		Logger logger = Logger.getLogger(LogInitializer.class);
		logger.info("Initialized logging.");
	}
}
