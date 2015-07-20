package cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.statistics.processors;

import java.util.List;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.statistics.StatisticsPersister;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.statistics.StatisticsProcessor;

public class StatisticsProcessorImpl<T> implements StatisticsProcessor<T> {

	private final Class<T> genericClass;
	private final String statisticId;
	private final List<StatisticsValueProcessor<T>> valueProcessors;

	public StatisticsProcessorImpl(String statisticId, List<StatisticsValueProcessor<T>> valueProcessors,
			Class<T> genericClass) {
		this.statisticId = statisticId;
		this.valueProcessors = valueProcessors;
		this.genericClass = genericClass;
	}

	@Override
	public String getStatisticId() {
		return statisticId;
	}

	@Override
	public void process(T value) {
		synchronized (valueProcessors) { // TODO try to improve performance
			for (StatisticsValueProcessor<T> valueProcessor : valueProcessors) {
				valueProcessor.process(value);
			}
		}
	}

	@Override
	public void persist(StatisticsPersister persister) {
		persister.startStatistic(statisticId, genericClass);

		synchronized (valueProcessors) {
			for (StatisticsValueProcessor<T> valueProcessor : valueProcessors) {
				valueProcessor.persist(persister);
			}
		}

		persister.endStatistic();
	}
}
