package cz.cuni.mff.d3s.jdeeco.cloudsimulator.service;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

@WebService
@SOAPBinding(style = Style.RPC)
public interface SimulatorService {

	/**
	 * 
	 * @return simulation ID
	 */
	@WebMethod String CreateSimulation();
	
	@WebMethod void StartSimulation(String simulationID);
	
	// TODO return status
	@WebMethod void GetStatus(String simulationID);
}
