package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.services;

import java.util.Date;
import java.util.List;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.models.ExecutionEndSpecificationType;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.models.SimulationExecution;

public interface SimulationExecutionService {
	List<SimulationExecution> listExecutions();

	SimulationExecution getExecutionById(int executionId);

	SimulationExecution executeConfiguration(int configurationId, String description, Integer executionRunCount,
			ExecutionEndSpecificationType endSpecificationType, Date endDate);
	
	void setDescription(int executionId, String description);

	void stopExecution(int executionId);
}
