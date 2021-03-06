package cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.models;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.data.StatisticsSaveMode;

/**
 * SimulationRunStatisticAggdataId generated by hbm2java
 */
public class SimulationRunStatisticAggdataId implements java.io.Serializable {

	private static final long serialVersionUID = -7546424945410311118L;

	private long runStatisticId;
	private StatisticsSaveMode saveType;

	public SimulationRunStatisticAggdataId() {
	}

	public SimulationRunStatisticAggdataId(long runStatisticId, StatisticsSaveMode saveType) {
		this.runStatisticId = runStatisticId;
		this.saveType = saveType;
	}

	public long getRunStatisticId() {
		return this.runStatisticId;
	}

	public void setRunStatisticId(long runStatisticId) {
		this.runStatisticId = runStatisticId;
	}

	public StatisticsSaveMode getSaveType() {
		return this.saveType;
	}

	public void setSaveType(StatisticsSaveMode saveType) {
		this.saveType = saveType;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof SimulationRunStatisticAggdataId))
			return false;
		SimulationRunStatisticAggdataId castOther = (SimulationRunStatisticAggdataId) other;

		return (this.getRunStatisticId() == castOther.getRunStatisticId())
				&& (this.getSaveType() == castOther.getSaveType());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (int) this.getRunStatisticId();
		result = 37 * result + this.getSaveType().hashCode();
		return result;
	}

}
