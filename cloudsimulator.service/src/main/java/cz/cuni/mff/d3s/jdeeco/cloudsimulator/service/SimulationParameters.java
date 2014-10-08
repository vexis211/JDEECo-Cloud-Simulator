package cz.cuni.mff.d3s.jdeeco.cloudsimulator.service;

import javax.xml.bind.annotation.XmlType;

@XmlType(name = "SimulationParameters")
public class SimulationParameters {
	private String applicationDataId;

	public String getApplicationDataId() {
		return applicationDataId;
	}

	public SimulationParameters(String applicationDataId){
		this.applicationDataId = applicationDataId;
	}
}
