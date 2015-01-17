package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.cloud;

public interface CloudMachineService {
	
	CloudMachine getMachineWithName(String machineName);
	CloudMachineBuilder buildMachineFromTemplate(String templateName, String machineName);
	
	void deleteMachine(CloudMachine machine);
}
