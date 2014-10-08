package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager;

import javax.xml.ws.Endpoint;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.service.SimulatorServiceImpl;

/**
 * Hello world!
 *
 */
public class App {

	public static final String HOST_NAME = "simulator"; 
	public static final String HOST_URI = "http://localhost:9999"; 
	
	public static String getServiceURI() {
		return String.format("%s/ws/%s", HOST_URI, HOST_NAME);
	}
	
	public static void main(String[] args) {
		String serviceURI = getServiceURI();
		
		System.out.println(String.format("Publishing service: \"%s\".", serviceURI));
		Endpoint.publish(serviceURI, new SimulatorServiceImpl());

	}
}
