package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine.cloud;

public interface MachineCloudService {
	
	CloudMachine getMachineWithName(String machineName);
	CloudMachineBuilder buildMachineFromTemplate(String templateName, String machineName);
}
