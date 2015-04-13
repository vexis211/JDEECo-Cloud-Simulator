
package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager;

import java.io.FileNotFoundException;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.Log4jConfigurer;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.JobManagerEngine;

public class App {

	public static void main(String[] args) throws FileNotFoundException {
        // configure logger from XML configuration file
        Log4jConfigurer.initLogging("classpath:configuration/log4j.xml");
		
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("configuration/application-context.xml");
        
		JobManagerEngine engine = (JobManagerEngine) context.getBean("jobManagerEngine");
		engine.start();
		
		context.close();
	}
}
