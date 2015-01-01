package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.engine;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

public class EngineStartup {

	@Resource
	private JobManagerEngine jobManagerEngine;
	
	private boolean isStarted = false;
	private Thread engineThread;
	
	@PostConstruct
	public void initialize() {
		if (isStarted) return;
		isStarted = true;
		
		Runnable runEngine = () -> this.jobManagerEngine.start();
		
		this.engineThread = new Thread(runEngine);
		this.engineThread.start();
	}
}