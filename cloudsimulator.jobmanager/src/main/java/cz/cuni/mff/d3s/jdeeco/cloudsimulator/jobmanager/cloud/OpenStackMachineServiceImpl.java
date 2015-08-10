package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.cloud;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.openstack4j.model.compute.Action;
import org.openstack4j.model.compute.Server;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.cloud.OpenStackConnector;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.workers.WorkersCommon;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.cloud.OpenStackComponent;

public class OpenStackMachineServiceImpl extends OpenStackComponent implements OpenStackMachineService {

	private final HashMap<String, OpenStackMachine> machinesByName = new HashMap<>();
	private final HashMap<String, OpenStackMachine> machinesById = new HashMap<>();

	public OpenStackMachineServiceImpl(OpenStackConnector openStackConnector) {
		super(openStackConnector);
	}

	@Override
	public List<CloudMachine> listMachines() {
		updateFromCloud();

		return machinesByName.values().stream().collect(Collectors.toList());
	}

	@Override
	public OpenStackMachine getMachineWithName(String machineName) {
		if (machinesByName.containsKey(machineName)) {
			return machinesByName.get(machineName);
		}

		updateFromCloud();

		return machinesByName.containsKey(machineName) ? machinesByName.get(machineName) : null;
	}

	private void updateFromCloud() {
		getClient().compute().servers().list().stream().filter(x -> !machinesById.containsKey(x.getId()))
				.filter(x -> x.getName().startsWith(WorkersCommon.ID_PREFIX))
				.forEach(x -> addMachine(x, x.getName()));
	}

	private OpenStackMachine addMachine(Server server, String machineName) {
		OpenStackMachine machine = new OpenStackMachine(server, machineName);
		machinesById.put(machine.getId(), machine);
		machinesByName.put(machine.getName(), machine);
		return machine;
	}

	@Override
	public OpenStackMachineBuilder buildMachineFromTemplate(String machineName, CloudMachineBuilderParams builderParams) {
		return new OpenStackMachineBuilder(openStackConnector, this, machineName, builderParams);
	}

	@Override
	public OpenStackMachine registerCreatedMachine(Server newServer, String machineName) {
		return addMachine(newServer, machineName);
	}

	@Override
	public void startMachine(CloudMachine machine) {
		getClient().compute().servers().action(machine.getId(), Action.START);
	}

	@Override
	public void stopMachine(CloudMachine machine) {
		getClient().compute().servers().action(machine.getId(), Action.STOP);
	}

	@Override
	public void deleteMachine(CloudMachine machine) {
		machinesById.remove(machine.getId());
		machinesByName.remove(machine.getName());

		getClient().compute().servers().delete(machine.getId());
	}
}
