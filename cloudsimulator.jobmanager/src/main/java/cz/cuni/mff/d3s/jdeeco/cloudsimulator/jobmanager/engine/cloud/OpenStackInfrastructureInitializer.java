package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.cloud;

public class OpenStackInfrastructureInitializer {
	
	public static void main(String[] args) {
		OpenStackInfrastructureInitializer initializer = new OpenStackInfrastructureInitializer();
		initializer.initialize();
	}


	private void initialize() {
		connect();
		
		createNetworkInfrastructure();
		createStorage();
		
		createJobManager();
		createInitialWorkers();
		
		disconnect();
	}


	private void connect() {
		// TODO Auto-generated method stub
		
	}
	
	private void disconnect() {
		// TODO Auto-generated method stub
		
	}



	private void createNetworkInfrastructure() {
		// TODO Auto-generated method stub
		
	}
	
	private void createStorage() {
		// TODO Auto-generated method stub
		
	}

	private void createJobManager() {
		// TODO Auto-generated method stub
		
	}

	private void createInitialWorkers() {
		// TODO Auto-generated method stub
		
	}



	private void clean() {
	}
}
