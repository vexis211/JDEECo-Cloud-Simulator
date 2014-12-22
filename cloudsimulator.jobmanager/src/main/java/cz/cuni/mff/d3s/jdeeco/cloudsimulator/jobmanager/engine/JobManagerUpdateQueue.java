package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.updates.JobManagerUpdate;

public class JobManagerUpdateQueue extends LinkedBlockingQueue<JobManagerUpdate> {

	private static final long serialVersionUID = 8870130257870687368L;

    public List<JobManagerUpdate> takeAll() throws InterruptedException {
    	
    	// take first if there is no update wait
    	JobManagerUpdate firstItem = this.take();

    	List<JobManagerUpdate> updates = new ArrayList<JobManagerUpdate>();
    	updates.add(firstItem);
    	
    	JobManagerUpdate nextUpdate;
    	while ((nextUpdate = this.poll()) != null) {
			updates.add(nextUpdate);
		}
    	    	
        return updates;
    }
}
