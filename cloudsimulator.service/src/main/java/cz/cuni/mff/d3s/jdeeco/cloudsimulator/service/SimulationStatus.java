package cz.cuni.mff.d3s.jdeeco.cloudsimulator.service;

import javax.xml.bind.annotation.XmlType;

@XmlType(name = "SimulationStatus")
public class SimulationStatus {
	private Status status;

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	
	public enum Status {
		Running, Completed,
	}
}
