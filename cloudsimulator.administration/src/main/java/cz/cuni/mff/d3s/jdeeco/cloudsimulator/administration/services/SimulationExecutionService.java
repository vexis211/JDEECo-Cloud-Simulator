package cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.services;

import java.util.Date;
import java.util.List;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.data.ExecutionEndSpecificationType;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.models.SimulationExecution;

public interface SimulationExecutionService {
	List<SimulationExecution> listExecutions();
	List<SimulationExecution> listAllNotCompletedExecutions();

	SimulationExecution getExecutionById(int executionId);

	SimulationExecution executeConfiguration(int configurationId, String description, Integer executionRunMultiplicator,
			ExecutionEndSpecificationType endSpecificationType, Date endDate, String runProfile,
			String statisticsProfile, String assertsProfile);
	
	void setDescription(int executionId, String description);

	void stopExecution(int executionId);
}
