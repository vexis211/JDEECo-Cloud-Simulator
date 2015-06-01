package cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.statistics.processors;

import java.util.ArrayList;
import java.util.List;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.statistics.StatisticsPersister;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.statistics.StatisticsSaveMode;

public class StatisticsVectorProcessor<T> implements StatisticsValueProcessor<T> {

	// TODO needs to be saved outside the memory - it can be really big!!!
	private List<T> values = new ArrayList<T>();
	
	@Override
	public void process(T value) {
		values.add(value);
	}

	@Override
	public void persist(StatisticsPersister<T> persister) {
		persister.startPersistingVector(StatisticsSaveMode.Vector);
		for (T value : values) {
			persister.persistVectorValue(value);
		}
		persister.endPersistingVector();
	}
}
