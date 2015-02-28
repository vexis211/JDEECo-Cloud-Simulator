package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web.client.data;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.ExtendedDateFormat;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.models.ExecutionEndSpecificationType;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.SimulationStatus;

public interface SimulationExecutionItem {

	public static final String TODATE_FORMAT_STRING = "dd/MM/yyyy HH:mm:ss";
	public static final ExtendedDateFormat TODATE_FORMAT = new ExtendedDateFormat(new SimpleDateFormat(TODATE_FORMAT_STRING), false);

	int getId();
	void setId(int id);
	
	Date getCreated();
	String getCreator();

	String getDescription();
	void setDescription(String description);

	SimulationStatus getStatus();
	String getStatusDesc();
	
	Date getStartedDate();	
	Date getEndedDate();

	Integer getRunCount();
	void setRunCount(Integer runCount);

	ExecutionEndSpecificationType getEndSpecificationType();
	void setEndSpecificationType(ExecutionEndSpecificationType endSpecificationType);

	Date getEndDate();
	void setEndDate(Date endDate);

	String getEndDateString();
	void setEndDateString(String endDateString);
	
	String getEndSpecificationDesc();
	
	List<SimulationRunItem> getRuns();	
	void addRun(SimulationRunItem run);
}
