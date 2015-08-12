package cz.cuni.mff.d3s.jdeeco.cloudsimulator.worker;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WorkerInfoProviderImpl implements WorkerInfoProvider {

	private static final Logger logger = LoggerFactory.getLogger(WorkerInfoProviderImpl.class);

	private String workerId;
	
	@Override
	public String getWorkerId() {
		return workerId == null ? (workerId = getWorkerIdInternal()) : workerId;
	}

	private String getWorkerIdInternal(){
		String workerId = "Unknown";

		logger.info("Getting worker ID...");
		try
		{
		    InetAddress addr = InetAddress.getLocalHost();
		    workerId = addr.getHostName();

			logger.info("Worker ID: '{}'.", workerId);
		}
		catch (UnknownHostException ex)
		{
			logger.error("Cannot get worker ID.", ex);
		}
		
		return workerId;
	}
}
