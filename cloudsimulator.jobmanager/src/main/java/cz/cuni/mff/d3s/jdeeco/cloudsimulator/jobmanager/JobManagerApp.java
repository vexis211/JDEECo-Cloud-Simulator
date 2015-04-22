
package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager;

import java.io.FileNotFoundException;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.ServerApp;

public class JobManagerApp extends ServerApp {

	public static void main(String[] args) throws FileNotFoundException {
        ServerApp app = new JobManagerApp();
        app.run();
	}
	
	@Override
	protected String getServerEngineBeanName() {
		return "jobManagerEngine";
	}
}
