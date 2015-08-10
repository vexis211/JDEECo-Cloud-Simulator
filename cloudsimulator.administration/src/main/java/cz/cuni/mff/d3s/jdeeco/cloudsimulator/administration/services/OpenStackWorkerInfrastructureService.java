package cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.services;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.joda.time.DateTime;
import org.openstack4j.model.compute.Server;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.infrastructure.WorkerData;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.infrastructure.WorkerDataImpl;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.infrastructure.WorkerPowerState;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.cloud.OpenStackComponent;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.cloud.OpenStackConnector;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.workers.WorkersCommon;

class OpenStackWorkerInfrastructureService extends OpenStackComponent implements WorkerInfrastructureService {

	public OpenStackWorkerInfrastructureService(OpenStackConnector openStackConnector) {
		super(openStackConnector);
	}

	private Stream<? extends Server> listServers() {
		return getNewClient().compute().servers().list().stream();
	}

	@Override
	public List<WorkerData> getWorkerDatas() {

		Stream<? extends Server> servers = listServers().filter(x -> x.getName().startsWith(WorkersCommon.ID_PREFIX));

		return servers.map(x -> toWorkerData(x)).collect(Collectors.toList());
	}

	private WorkerData toWorkerData(Server server) {

		Integer powerStateInt = Integer.valueOf(server.getPowerState());
		WorkerPowerState powerState = WorkerPowerState.fromInt(powerStateInt);

		return new WorkerDataImpl(server.getName(), powerState, new DateTime(server.getCreated()),
				new DateTime(server.getLaunchedAt()));
	}
}
