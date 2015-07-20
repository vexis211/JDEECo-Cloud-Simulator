package cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.statistics;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.data.StatisticsSaveMode;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.statistics.processors.StatisticsAvgProcessor;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.statistics.processors.StatisticsCountProcessor;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.statistics.processors.StatisticsMaxNumberProcessor;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.statistics.processors.StatisticsMinNumberProcessor;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.statistics.processors.StatisticsProcessorImpl;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.statistics.processors.StatisticsSumProcessor;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.statistics.processors.StatisticsValueProcessor;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.statistics.processors.StatisticsVectorProcessor;

public class StatisticsProcessorNumberFactoryImpl<T extends Number> implements StatisticsProcessorFactory<T> {

	private final Class<T> genericClass;

	public StatisticsProcessorNumberFactoryImpl(Class<T> genericClass) {
		this.genericClass = genericClass;
	}

	@Override
	public StatisticsProcessor<T> create(String statisticId, EnumSet<StatisticsSaveMode> saveMode) {

		List<StatisticsValueProcessor<T>> valueProcessors = new ArrayList<StatisticsValueProcessor<T>>();

		for (StatisticsSaveMode mode : saveMode) {
			switch (mode) {
			case Avg:
				valueProcessors.add(new StatisticsAvgProcessor<T>());
			case Count:
				valueProcessors.add(new StatisticsCountProcessor<T>());
				break;
			case Max:
				valueProcessors.add(new StatisticsMaxNumberProcessor<T>());
				break;
			case Min:
				valueProcessors.add(new StatisticsMinNumberProcessor<T>());
				break;
			case Sum:
				valueProcessors.add(new StatisticsSumProcessor<T>());
				break;
			case Vector:
				valueProcessors.add(new StatisticsVectorProcessor<T>());
				break;

			default:
				throw new EnumConstantNotPresentException(mode.getClass(), mode.toString());
			}
		}

		return new StatisticsProcessorImpl<T>(statisticId, valueProcessors, genericClass);
	}

}
