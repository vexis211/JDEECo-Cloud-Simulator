package cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web.client.data;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.ExtendedDateFormat;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.data.SimulationStatus;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.data.ExecutionEndSpecificationType;

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

	Integer getRunMultiplicator();
	void setRunMultiplicator(Integer runMultiplicator);

	ExecutionEndSpecificationType getEndSpecificationType();
	void setEndSpecificationType(ExecutionEndSpecificationType endSpecificationType);

	Date getEndDate();
	void setEndDate(Date endDate);

	String getEndDateString();
	void setEndDateString(String endDateString);
	
	String getEndSpecificationDesc();

	String getRunProfile();
	void setRunProfile(String runProfile);

	String getStatisticsProfile();
	void setStatisticsProfile(String statisticsProfile);

	String getAssertsProfile();
	void setAssertsProfile(String assertsProfile);
	
	List<SimulationRunItem> getRuns();	
	void addRun(SimulationRunItem run);
}
