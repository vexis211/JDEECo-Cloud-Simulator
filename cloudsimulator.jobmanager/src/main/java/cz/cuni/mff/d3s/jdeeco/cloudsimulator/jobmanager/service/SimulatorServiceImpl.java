package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.service;

import javax.jws.WebService;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.service.*;

@WebService(endpointInterface = "cz.cuni.mff.d3s.jdeeco.cloudsimulator.service.SimulatorService")
public class SimulatorServiceImpl implements SimulatorService {

	@Override
	public String CreateSimulation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void StartSimulation(String simulationID) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void GetStatus(String simulationID) {
		// TODO Auto-generated method stub
		
	}

}
