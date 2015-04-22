
package cz.cuni.mff.d3s.jdeeco.cloudsimulator.worker;

import java.io.FileNotFoundException;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.ServerApp;


public class WorkerApp extends ServerApp {

	public static void main(String[] args) throws FileNotFoundException {
        ServerApp app = new WorkerApp();
        app.run();
	}
	
	@Override
	protected String getServerEngineBeanName() {
		return "workerEngine";
	}
}