package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web.client.data;

import java.util.Date;
import java.util.List;

public interface ConfigurationItem {

	public int getId();

	public String getName();

	public void setName(String name);

	public String getStatus();
	
	public Date getLastRunDate();

	public List<ExecutionItem> getExecutions();	
	public void addExecution(ExecutionItem execution);
}
