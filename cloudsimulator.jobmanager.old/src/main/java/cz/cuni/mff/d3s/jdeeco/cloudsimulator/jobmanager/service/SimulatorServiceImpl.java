package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.service;

import javax.activation.DataHandler;
import javax.jws.WebService;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.service.*;

@WebService(endpointInterface = "cz.cuni.mff.d3s.jdeeco.cloudsimulator.service.SimulatorService")
public class SimulatorServiceImpl implements SimulatorService {

	@Override
	public String uploadApplication(DataHandler application) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String createSimulation(SimulationParameters parameters) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void startSimulation(String simulationId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public SimulationStatus getStatus(String simulationId) {
		// TODO Auto-generated method stub
		return null;
	}
}
