package cz.cuni.mff.d3s.jdeeco.cloudsimulator.worker.engine;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers.tasks.WorkerTask;

public class WorkerTaskQueue extends LinkedBlockingQueue<WorkerTask> {

	private static final long serialVersionUID = 8870130257870687368L;

    public List<WorkerTask> takeAll() throws InterruptedException {
    	
    	// take first if there is no update wait
    	WorkerTask firstItem = this.take();

    	List<WorkerTask> tasks = new ArrayList<WorkerTask>();
    	tasks.add(firstItem);
    	
    	WorkerTask nextTask;
    	while ((nextTask = this.poll()) != null) {
			tasks.add(nextTask);
		}
    	    	
        return tasks;
    }
}
