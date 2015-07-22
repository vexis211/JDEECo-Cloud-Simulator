package cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.statistics;

import java.util.Collection;
import java.util.EnumSet;
import java.util.concurrent.ConcurrentHashMap;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.data.StatisticsSaveMode;

public class StatisticsManagerImpl implements StatisticsManager {

	private final ConcurrentHashMap<Class<?>, ProcessorStore<?>> processorStores = new ConcurrentHashMap<Class<?>, ProcessorStore<?>>();
	private final StatisticsConfiguration statisticsConfiguration;
	private final StatisticsProcessorFactoryProvider statisticsProcessorFactoryProvider;
	private final StatisticsPersister statisticsPersister;

	public StatisticsManagerImpl(StatisticsConfiguration statisticsConfiguration,
			StatisticsProcessorFactoryProvider statisticsProcessorFactoryProvider,
			StatisticsPersister statisticsPersister) {
		this.statisticsConfiguration = statisticsConfiguration;
		this.statisticsProcessorFactoryProvider = statisticsProcessorFactoryProvider;
		this.statisticsPersister = statisticsPersister;
	}

	@Override
	public <T> StatisticsProcessor<T> getGeneralProcessor(String statisticId, Class<T> clazz) {
		// get or create store
		if (!processorStores.containsKey(clazz)) {
			processorStores.putIfAbsent(clazz, new ProcessorStore<T>(statisticsProcessorFactoryProvider.getGeneral(clazz)));
		}
		ProcessorStore<T> store = getExistingStore(clazz);

		// get or create processor
		StatisticsProcessor<T> processor = store.getOrCreateProcessor(statisticId);

		return processor;
	}

	@Override
	public <T extends Comparable<T>> StatisticsProcessor<T> getComparableProcessor(String statisticId, Class<T> clazz) {
		// get or create store
		if (!processorStores.containsKey(clazz)) {
			processorStores.putIfAbsent(clazz,
					new ProcessorStore<T>(statisticsProcessorFactoryProvider.getForComparable(clazz)));
		}
		ProcessorStore<T> store = getExistingStore(clazz);

		// get or create processor
		StatisticsProcessor<T> processor = store.getOrCreateProcessor(statisticId);

		return processor;
	}

	@Override
	public <T extends Number> StatisticsProcessor<T> getNumberProcessor(String statisticId, Class<T> clazz) {
		// get or create store
		if (!processorStores.containsKey(clazz)) {
			processorStores.putIfAbsent(clazz, new ProcessorStore<T>(statisticsProcessorFactoryProvider.getForNumber(clazz)));
		}
		ProcessorStore<T> store = getExistingStore(clazz);

		// get or create processor
		StatisticsProcessor<T> processor = store.getOrCreateProcessor(statisticId);

		return processor;
	}

	@SuppressWarnings("unchecked")
	private <T> ProcessorStore<T> getExistingStore(Class<T> clazz) {
		ProcessorStore<T> store = (ProcessorStore<T>) processorStores.get(clazz); // TODO improvement - can this be done better?
		return store;
	}

	@Override
	public void persistStatistics() {
		// initialize persister
		statisticsPersister.start();

		// persist statistics
		for (ProcessorStore<?> store : processorStores.values()) {
			for (StatisticsProcessor<?> processor : store.getProcessors()) {
				processor.persist(statisticsPersister);
			}
		}

		// write endings, flush, save, ...
		statisticsPersister.end();
	}

	private class ProcessorStore<T> {

		private final ConcurrentHashMap<String, StatisticsProcessor<T>> processors = new ConcurrentHashMap<String, StatisticsProcessor<T>>();
		private final StatisticsProcessorFactory<T> processorFactory;

		public ProcessorStore(StatisticsProcessorFactory<T> processorFactory) {
			this.processorFactory = processorFactory;
		}

		StatisticsProcessor<T> getOrCreateProcessor(String statisticId) {
			if (!processors.containsKey(statisticId)) {
				EnumSet<StatisticsSaveMode> statisticSaveModes = statisticsConfiguration.getSaveModes(statisticId);
				processors.putIfAbsent(statisticId, processorFactory.create(statisticId, statisticSaveModes));
			}

			return processors.get(statisticId);
		}

		public Collection<StatisticsProcessor<T>> getProcessors() {
			return processors.values();
		}
	}
}
