package cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers;

import java.io.Serializable;

public interface SimulationId extends Serializable {
	int getExecutionId();
	int getRunId();
}
