
package cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers;

import java.io.FileNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.Log4jConfigurer;

public class ServerApp {

	private static Logger logger;
	
	public void run() throws FileNotFoundException {
		// configure logger from XML configuration file
        Log4jConfigurer.initLogging("classpath:configuration/log4j.xml");
        logger = LoggerFactory.getLogger(ServerApp.class);
        logger.info("Initialized logging.");
        
		logger.info("Creating application context...");
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("configuration/application-context.xml");

		logger.info("Resolving engine...");
		ServerEngine engine = (ServerEngine) context.getBean(getServerEngineBeanName());

		logger.info("Starting engine...");
		engine.start();

		logger.info("Destroying context...");		
		context.close();
	}

	protected String getServerEngineBeanName() {
		return "serverEngine";
	}
}
