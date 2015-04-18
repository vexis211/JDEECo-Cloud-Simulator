package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data;

import org.joda.time.DateTime;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.data.ExecutionEndSpecificationType;

public class ExecutionDeadlineSettings {
	
	private final ExecutionEndSpecificationType endSpecificationType;
	private final DateTime endDate;

	public ExecutionDeadlineSettings(ExecutionEndSpecificationType endSpecificationType, DateTime endDate) {
		this.endSpecificationType = endSpecificationType;
		this.endDate = endDate;
	}

	public ExecutionEndSpecificationType getEndSpecificationType() {
		return endSpecificationType;
	}

	public DateTime getEndDate() {
		return endDate;
	}
}