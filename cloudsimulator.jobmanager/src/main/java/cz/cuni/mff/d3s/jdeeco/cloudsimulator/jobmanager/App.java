
package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager;

import java.io.FileNotFoundException;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.Log4jConfigurer;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.JobManagerEngine;

public class App {

	
	public static void main(String[] args) throws FileNotFoundException {

		// configure logger from XML configuration file
        Log4jConfigurer.initLogging("classpath:configuration/log4j.xml");
        
    	Logger logger = Logger.getLogger(App.class);
		logger.info("Initialized logging.");

		logger.info("Creating application context...");
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("configuration/application-context.xml");

		logger.info("Resolving engine...");
		JobManagerEngine engine = (JobManagerEngine) context.getBean("jobManagerEngine");

		logger.info("Starting engine...");
		engine.start();

		logger.info("Destroying context...");		
		context.close();
	}
}
