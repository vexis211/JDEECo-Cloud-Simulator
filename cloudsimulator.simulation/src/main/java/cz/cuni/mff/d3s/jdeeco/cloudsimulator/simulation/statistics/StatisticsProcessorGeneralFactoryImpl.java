package cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.statistics;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.statistics.processors.StatisticsCountProcessor;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.statistics.processors.StatisticsProcessorImpl;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.statistics.processors.StatisticsValueProcessor;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.statistics.processors.StatisticsVectorProcessor;

public class StatisticsProcessorGeneralFactoryImpl<T> implements StatisticsProcessorFactory<T> {

	private static Logger logger = LoggerFactory.getLogger(StatisticsProcessorGeneralFactoryImpl.class);

	@Override
	public StatisticsProcessor<T> create(String statisticId, EnumSet<StatisticsSaveMode> saveMode) {

		List<StatisticsValueProcessor<T>> valueProcessors = new ArrayList<StatisticsValueProcessor<T>>();

		for (StatisticsSaveMode mode : saveMode) {
			switch (mode) {
			case Avg:
			case Max:
			case Min:
			case Sum:
				logger.error("Statistics value processor cannot be added for save mode '{}' for statistic id '{}'. "
						+ "Type of statistic value is not compatible with statistics value processor", mode,
						statisticId);
				break;
			case Count:
				valueProcessors.add(new StatisticsCountProcessor<T>());
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
