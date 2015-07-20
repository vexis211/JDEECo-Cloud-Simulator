package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.statistics;

public interface RunStatistic {
	String getName();
	void accept(RunStatisticVisitor visitor);
}
