package cz.cuni.mff.d3s.jdeeco.cloudsimulator.worker;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Log4jConfigurer;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.ServerApp;

public class Test {

	private static Logger logger;

	public static void main(String[] args) throws IOException {
        Log4jConfigurer.initLogging("classpath:configuration/log4j.xml");
        logger = LoggerFactory.getLogger(ServerApp.class);
        
        logger.info("Starting test!");
        
		WorkerInfoProvider workerInfoProvider = new WorkerInfoProviderImpl();
		String workerId = workerInfoProvider.getWorkerId();
		
		logger.info("Worker ID: {}", workerId);
		
		System.in.read();
		
        logger.info("Exiting test!");
	}
}
