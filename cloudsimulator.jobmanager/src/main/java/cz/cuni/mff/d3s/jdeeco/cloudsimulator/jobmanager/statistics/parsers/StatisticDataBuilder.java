package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.statistics.parsers;

interface StatisticDataBuilder {
	boolean isInTerminalState();

	void processLine(String line);
}