package cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.statistics;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.statistics.processors.StatisticsCountProcessor;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.statistics.processors.StatisticsMaxProcessor;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.statistics.processors.StatisticsMinProcessor;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.statistics.processors.StatisticsProcessorImpl;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.statistics.processors.StatisticsValueProcessor;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.statistics.processors.StatisticsVectorProcessor;

public class StatisticsProcessorComparableFactoryImpl<T extends Comparable<T>> implements StatisticsProcessorFactory<T> {

	private static Logger logger = LogManager.getLogger(StatisticsProcessorComparableFactoryImpl.class);

	@Override
	public StatisticsProcessor<T> create(String statisticId, EnumSet<StatisticsSaveMode> saveMode) {

		List<StatisticsValueProcessor<T>> valueProcessors = new ArrayList<StatisticsValueProcessor<T>>();

		for (StatisticsSaveMode mode : saveMode) {
			switch (mode) {
			case Avg:
			case Sum:
				logger.error(String.format(
						"Statistics value processor cannot be added for save mode '%s' for statistic id '%s'. "
								+ "Type of statistic value is not compatible with statistics value processor", mode,
						statisticId));
				break;
			case Count:
				valueProcessors.add(new StatisticsCountProcessor<T>());
				break;
			case Max:
				valueProcessors.add(new StatisticsMaxProcessor<T>());
				break;
			case Min:
				valueProcessors.add(new StatisticsMinProcessor<T>());
				break;
			case Vector:
				valueProcessors.add(new StatisticsVectorProcessor<T>());
				break;

			default:
				throw new EnumConstantNotPresentException(mode.getClass(), mode.toString());
			}
		}

		return new StatisticsProcessorImpl<T>(statisticId, valueProcessors);
	}

}
