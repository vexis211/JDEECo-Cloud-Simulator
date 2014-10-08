package cz.cuni.mff.d3s.jdeeco.cloudsimulator.service;

import javax.activation.DataHandler;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.xml.bind.annotation.XmlMimeType;
import javax.xml.ws.soap.MTOM;

@MTOM
@WebService
@SOAPBinding(style = Style.RPC)
public interface SimulatorService {

	/**
	 * Upload application ZIP file.
	 * 
	 * @param applicationData
	 *            application data in JAR
	 * @return application data ID
	 */
	@WebMethod
	String uploadApplication(
			@XmlMimeType("application/octet-stream") DataHandler application);

	/**
	 * Creates simulation with provided parameters.
	 * 
	 * @param parameters
	 *            parameters of simulation
	 * @return simulation ID
	 */
	@WebMethod
	String createSimulation(SimulationParameters parameters);

	/**
	 * Start created simulation.
	 * 
	 * @param simulationId
	 *            simulation ID
	 */
	@WebMethod
	void startSimulation(String simulationId);

	/**
	 * Get status of simulation.
	 * 
	 * @param simulationId
	 *            simulation ID
	 * @return current status of simulation
	 */
	@WebMethod
	SimulationStatus getStatus(String simulationId);
}
