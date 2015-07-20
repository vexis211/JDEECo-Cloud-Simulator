package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.statistics;

import java.util.Map;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.data.StatisticsSaveMode;

public interface RunStatisticVisitor {

	<T> void visit(Map<StatisticsSaveMode, T> aggregatedValues, T[] valuesVector);
}
