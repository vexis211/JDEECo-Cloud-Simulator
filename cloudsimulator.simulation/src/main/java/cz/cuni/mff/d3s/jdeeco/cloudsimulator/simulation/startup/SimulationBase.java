package cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.startup;

import java.io.File;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cz.cuni.mff.d3s.deeco.runners.DEECoSimulation;
import cz.cuni.mff.d3s.deeco.timer.DiscreteEventTimer;
import cz.cuni.mff.d3s.deeco.timer.SimulationTimer;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.asserts.Assert;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.asserts.AssertHandler;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.data.SimulationEndSpecificationType;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.settings.SimulationEndSettings;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.statistics.StatisticsManager;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.statistics.Statistics;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.statistics.StatisticsHandler;
import cz.cuni.mff.d3s.jdeeco.network.Network;
import cz.cuni.mff.d3s.jdeeco.network.device.SimpleBroadcastDevice;
import cz.cuni.mff.d3s.jdeeco.network.l2.strategy.KnowledgeInsertingStrategy;
import cz.cuni.mff.d3s.jdeeco.network.omnet.OMNeTSimulation;
import cz.cuni.mff.d3s.jdeeco.publishing.DefaultKnowledgePublisher;

public abstract class SimulationBase {

	private static Logger logger = Logger.getLogger(SimulationBase.class);

	private ClassPathXmlApplicationContext context;
	private StatisticsManager statisticsManager;
	private SimulationRunConfiguration runConfiguration;

	public void start() {

		// application context
		logger.info("Creating application context...");
		this.context = new ClassPathXmlApplicationContext("configuration/application-context.xml");

		configure();
		run();
		finish();
	}

	private void configure() {
		// directory for statistics, ...
		File resultsDir = new File("results"); // TODO improvement - from settings
		resultsDir.mkdirs();

		// configure statistics
		Statistics.Handler = (StatisticsHandler) context.getBean("statisticsHandler");
		this.statisticsManager = (StatisticsManager) context.getBean("statisticsManager");

		// configure asserts
		Assert.Handler = (AssertHandler) context.getBean("assertHandler");

		// run configuration
		this.runConfiguration = (SimulationRunConfiguration) context.getBean("simulationRunConfiguration");
	}

	private void run() {
		DEECoSimulation simulation = configureSimulation(runConfiguration.getProfileId());

		SimulationEndSettings endSettings = runConfiguration.getEndSettings();

		logger.info("Starting simulation...");
		if (endSettings.getType() == SimulationEndSpecificationType.SimulationTime) {
			// stop on simulation time is solved inside simulation environment
			simulation.start(endSettings.getTime());
		} else {

			java.util.concurrent.ExecutorService executor = java.util.concurrent.Executors.newSingleThreadExecutor();

			try {
				Runnable runnable = () -> simulation.start(Long.MAX_VALUE);
				java.util.concurrent.Future<?> future = executor.submit(runnable);

				// this will wait but only for specified time
				future.get(endSettings.getTime(), java.util.concurrent.TimeUnit.MILLISECONDS);
			} catch (final InterruptedException e) {
				// The thread was interrupted during sleep, wait or join
				logger.error("Error occurred while simulating.", e);
			} catch (final java.util.concurrent.TimeoutException e) {
				// Took too long!
				logger.info("Simulation stopped - time limit exceeded.");
			} catch (final java.util.concurrent.ExecutionException e) {
				// An exception from within the Runnable task
				logger.error("Error occurred while simulating.", e);
			} finally {
				executor.shutdown();
			}
		}
	}

	private void finish() {
		if (statisticsManager != null) {
			statisticsManager.persistStatistics();
			statisticsManager = null;
		}

		if (context != null) {
			logger.info("Destroying context...");
			context.close();
			context = null;
		}
	}

	protected abstract DEECoSimulation configureSimulation(String profileId);

	protected DEECoSimulation createSimulationWithDeeco() {
		SimulationTimer simulationTimer = new DiscreteEventTimer(); // also "new WallTimeSchedulerNotifier()"

		DEECoSimulation simulation = new DEECoSimulation(simulationTimer);
		simulation.addPlugin(new SimpleBroadcastDevice());
		simulation.addPlugin(Network.class);
		simulation.addPlugin(DefaultKnowledgePublisher.class);
		simulation.addPlugin(KnowledgeInsertingStrategy.class);

		return simulation;
	}

	protected DEECoSimulation createSimulationWithOmnet() {
		OMNeTSimulation omnet = new OMNeTSimulation();

		// Create main application container
		DEECoSimulation simulation = new DEECoSimulation(omnet.getTimer());
		simulation.addPlugin(Network.class);
		simulation.addPlugin(KnowledgeInsertingStrategy.class);
		simulation.addPlugin(DefaultKnowledgePublisher.class);
		simulation.addPlugin(omnet);

		return simulation;
	}

	// protected DEECoSimulation createSimulationWithMatsim() {
	// MATSimSimulation matSim = new MATSimSimulation("input/config.xml");
	//
	// // Create main application container
	// DEECoSimulation simulation = new DEECoSimulation(matSim.getTimer());
	//
	// // Add MATSim plug-in for all nodes
	// realm.addPlugin(matSim);
	//
	// // Configure loop-back networking for all nodes
	// simulation.addPlugin(new SimpleBroadcastDevice(0, 0, 100000, SimpleBroadcastDevice.DEFAULT_MTU));
	// simulation.addPlugin(Network.class);
	// simulation.addPlugin(DefaultKnowledgePublisher.class);
	// simulation.addPlugin(KnowledgeInsertingStrategy.class);
	//
	// return simulation;
	// }

	// protected DEECoSimulation createSimulationWithMatsimOmnet() {
	//
	// // Create joint OMNeT-MATSim simulation plug-in
	// OMNeTMATSimSimulation omnetmatsim = new OMNeTMATSimSimulation("input/config.xml");
	//
	// // Create main application container
	// DEECoSimulation simulation = new DEECoSimulation(omnetmatsim);
	// simulation.addPlugin(Network.class);
	// simulation.addPlugin(KnowledgeInsertingStrategy.class);
	// simulation.addPlugin(DefaultKnowledgePublisher.class);
	// simulation.addPlugin(omnetmatsim);
	//
	// return simulation;
	// }
}
