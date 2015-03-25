package cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web.client.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.Out;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.data.SimulationStatus;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.data.ExecutionEndSpecificationType;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.models.SimulationExecution;

public class SimulationExecutionItemImpl implements SimulationExecutionItem {

	private int id;
	private Date created;
	private String creator;

	private String description;
	private ExecutionEndSpecificationType endSpecificationType = ExecutionEndSpecificationType.ToDate;
	private Date endDate;
	private String endDateString;

	private SimulationStatus status;
	private String statusDesc;
	private Date startedDate;
	private Date endedDate;
	private Integer runCount;

	private List<SimulationRunItem> runs = new ArrayList<SimulationRunItem>();

	public SimulationExecutionItemImpl() {
	}

	public SimulationExecutionItemImpl(SimulationExecution execution) {
		this.id = execution.getId();
		this.created = execution.getCreated();
		this.creator = execution.getCreator().getEmail();

		this.description = execution.getDescription();
		this.runCount = execution.getRunCount();
		this.endSpecificationType = execution.getEndSpecificationType();
		this.endDate = execution.getEndDate();
		if (this.endDate != null) {
			this.endDateString = TODATE_FORMAT.format(this.endDate);
		}

		this.status = execution.getStatus();
		this.statusDesc = execution.getStatus().toString(); // TODO improvement
															// - % done??
		this.startedDate = execution.getStarted();
		this.endedDate = execution.getEnded();
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public void setId(int id) {
		this.id = id;
	}

	@Override
	public Date getCreated() {
		return created;
	}

	@Override
	public String getCreator() {
		return creator;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public SimulationStatus getStatus() {
		return status;
	}

	@Override
	public String getStatusDesc() {
		return statusDesc;
	}

	@Override
	public Date getStartedDate() {
		return startedDate;
	}

	@Override
	public Date getEndedDate() {
		return endedDate;
	}

	@Override
	public Integer getRunCount() {
		return runCount;
	}

	@Override
	public void setRunCount(Integer runCount) {
		this.runCount = runCount;
	}

	@Override
	public ExecutionEndSpecificationType getEndSpecificationType() {
		return endSpecificationType;
	}

	@Override
	public void setEndSpecificationType(ExecutionEndSpecificationType endSpecificationType) {
		this.endSpecificationType = endSpecificationType;
	}

	@Override
	public Date getEndDate() {
		return endDate;
	}

	@Override
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
		this.endDateString = endDate != null ? TODATE_FORMAT.format(this.endDate) : null;
	}

	@Override
	public String getEndDateString() {
		return endDateString;
	}

	@Override
	public void setEndDateString(String endDateString) {
		this.endDateString = endDateString;
		
		Out<Date> parserdDate = new Out<Date>();
		if (TODATE_FORMAT.tryParse(endDateString, parserdDate)) {
			this.endDate = parserdDate.get();
		} else {
			this.endDate = null;
		}
	}

	@Override
	public String getEndSpecificationDesc() {
		switch (endSpecificationType) {
		case AsCheapAsPossible:
			return "As cheap as possible";
		case AsFastAsPossible:
			return "As fast as possible";
		case ToDate:
			String toDateString = endDate != null ? TODATE_FORMAT.format(endDate) : "not specified";
			return "To date: " + toDateString;
		default:
			throw new RuntimeException(String.format("Value '%s' is not currently supported.", endSpecificationType));
		}
	}

	@Override
	public List<SimulationRunItem> getRuns() {
		return runs;
	}

	@Override
	public void addRun(SimulationRunItem run) {
		runs.add(run);
	}
}
