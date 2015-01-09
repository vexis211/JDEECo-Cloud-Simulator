package cz.cuni.mff.d3s.jdeeco.cloudsimulator.worker;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class WorkerInfoProviderImpl implements WorkerInfoProvider {

	private String workerId;
	
	@Override
	public String getWorkerId() {
		return workerId == null ? (workerId = getWorkerIdInternal()) : workerId;
	}

	private String getWorkerIdInternal(){
		String workerId = "Unknown";

		try
		{
		    InetAddress addr = InetAddress.getLocalHost();
		    workerId = addr.getHostName();
		}
		catch (UnknownHostException ex)
		{
			ex.printStackTrace();
		    System.out.println("Hostname can not be resolved!");
		}
		
		return workerId;
	}
}
