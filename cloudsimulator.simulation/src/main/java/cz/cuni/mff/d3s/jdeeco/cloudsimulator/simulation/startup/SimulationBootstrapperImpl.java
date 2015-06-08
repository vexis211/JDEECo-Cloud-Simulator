package cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.startup;

import java.io.File;
import java.io.FileNotFoundException;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.Log4jConfigurer;

import cz.cuni.mff.d3s.deeco.annotations.processor.AnnotationProcessor;
import cz.cuni.mff.d3s.deeco.annotations.processor.AnnotationProcessorException;
import cz.cuni.mff.d3s.deeco.model.runtime.api.RuntimeMetadata;
import cz.cuni.mff.d3s.deeco.model.runtime.custom.RuntimeMetadataFactoryExt;
import cz.cuni.mff.d3s.deeco.runtime.RuntimeConfiguration;
import cz.cuni.mff.d3s.deeco.runtime.RuntimeConfiguration.Distribution;
import cz.cuni.mff.d3s.deeco.runtime.RuntimeConfiguration.Execution;
import cz.cuni.mff.d3s.deeco.runtime.RuntimeConfiguration.Scheduling;
import cz.cuni.mff.d3s.deeco.runtime.RuntimeFramework;
import cz.cuni.mff.d3s.deeco.runtime.RuntimeFrameworkBuilder;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.asserts.Assert;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.asserts.AssertHandler;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.statistics.StatisticsManager;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.statistics.Statistics;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.statistics.StatisticsHandler;

public class SimulationBootstrapperImpl implements SimulationBootstrapper {

	private static Logger logger;
	
	private static ClassPathXmlApplicationContext context;	
	private static StatisticsManager statisticsManager;

	@Override
	public void initializeLogging() throws FileNotFoundException {
		File logsDir = new File("logs"); // TODO improvement - from settings
		logsDir.mkdirs();

		// configure logger from XML configuration file
		Log4jConfigurer.initLogging("classpath:configuration/log4j.xml");
		logger = Logger.getLogger(SimulationBootstrapperImpl.class);
		logger.info("Initialized logging.");

		// application context
		logger.info("Creating application context...");
		context = new ClassPathXmlApplicationContext("configuration/application-context.xml");

		// directory for statistics, ...
		File resultsDir = new File("results"); // TODO improvement - from settings
		resultsDir.mkdirs();
		
		// configure statistics
		Statistics.Handler = (StatisticsHandler) context.getBean("statisticsHandler");
		statisticsManager = (StatisticsManager) context.getBean("statisticsManager");
		
		// configure asserts
		Assert.Handler = (AssertHandler) context.getBean("assertHandler");
	}

	@Override
	public void startSimulation(SimulationStartParameters startParameters) throws AnnotationProcessorException {
		logger.info("Starting simulation...");

		AnnotationProcessor processor = new AnnotationProcessor(RuntimeMetadataFactoryExt.eINSTANCE);
		RuntimeMetadata model = RuntimeMetadataFactoryExt.eINSTANCE.createRuntimeMetadata();

		processor.process(model, startParameters.getRootComponent());

		RuntimeConfiguration runtimeConfiguration = new RuntimeConfiguration(Scheduling.WALL_TIME, Distribution.LOCAL,
				Execution.SINGLE_THREADED);
		RuntimeFrameworkBuilder builder = new RuntimeFrameworkBuilder(runtimeConfiguration);
		RuntimeFramework runtime = builder.build(model);
		runtime.start();
	}

	public void dispose() {
		if (statisticsManager != null) {
			statisticsManager.persistStatistics();
		}
		
		if (context != null) {
			logger.info("Destroying context...");
			context.close();
		}
	}
}
