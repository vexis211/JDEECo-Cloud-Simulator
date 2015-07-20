package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.statistics.parsers;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.statistics.RunStatistic;

interface RunStatisticBuilder extends StatisticDataBuilderListener {
	RunStatistic[] build();
}
