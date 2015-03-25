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



/*
	private static final DateTime MIN_VALUE = new DateTime(new Date(0));
	private static final DateTime MAX_VALUE = new DateTime(new Date(Long.MAX_VALUE));
 
 public DateTime getCalculatedDeadline() {
 
	return calculatedDeadline;
}

private DateTime calculateDeadLine() {
	switch (endSpecificationType) {
	case ToDate:
		return endDate;
	case AsCheapAsPossible:
		return MAX_VALUE ;
	case AsFastAsPossible:
		return MIN_VALUE;
	default:
		throw new RuntimeException(String.format("Value '%s' is not currently supported.", endSpecificationType));
	}
}*/