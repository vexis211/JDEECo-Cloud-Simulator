package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.cloud;

public class OpenStackInfrastructureInitializerParameters {

	// login
	public static final String ENDPOINT = "https://openstack.d3s.mff.cuni.cz:5000/v2.0";
	public static final String USER = "skalicky";
	public static final String PASSWORD = "";
	public static final String TENANT_NAME = "skalicky";

    // networking
    public static final String INNER_NETWORK_NAME = "working_network";
    public static final String INNER_NETWORK_SUBNET_NAME = "working_subnet";
    public static final String OUTER_NETWORK_NAME = "public";
    public static final String ROUTER_NAME = "working_router";

    // create a keypair
    public static final String KEYPAIR_NAME = "ssh_keypair";

    // computing
    public static final String JOB_MANAGER_TEMPLATE_NAME = "TODO"; // TODO
    public static final String JOB_MANAGER_NAME = "job_manager";

    public static final int INITIAL_WORKER_COUNT = 3;
    public static final String WORKER_TEMPLATE_NAME = "TODO"; // TODO
    public static final String WORKER_FLAVOR_NAME = "Worker Flavor";
    
    // storage
	public static final String STORAGE_DATAPACKAGE_CONTAINER = "simulations/preparedData";
	
	/*
	 * 
openStack.storage.dataContainerName=simulations/preparedData
openStack.storage.resultsContainerName=simulations/runs/results
openStack.storage.logsContainerName=simulations/runs/logs
	 */
}
