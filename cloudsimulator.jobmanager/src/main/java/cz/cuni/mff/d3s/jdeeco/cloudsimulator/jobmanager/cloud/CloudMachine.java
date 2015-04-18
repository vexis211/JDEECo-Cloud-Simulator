package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.cloud;

public interface CloudMachine {
	
	/**
	 * Is used for identification of machines in cloud management.
	 * @return id of cloud machine
	 */
	String getId();
	/**
	 * Is used for identification of workers.
	 * @return name of cloud machine
	 */
	String getName();
	
	CloudMachineStatus getStatus();
}
