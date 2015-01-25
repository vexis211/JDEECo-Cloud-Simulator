package cz.cuni.mff.d3s.jdeeco.cloudsimulator.common;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

public class ExtendedTimerTask extends TimerTask {

	private List<Runnable> subTasks = new ArrayList<Runnable>();
	private ExtendedTimer extendedTimer; 
	
	public ExtendedTimerTask(ExtendedTimer extendedTimer) {
		this.extendedTimer = extendedTimer;
	}

	@Override
	public void run() {
		List<Runnable> toRun;
		synchronized (subTasks) {
			toRun = new ArrayList<Runnable>(subTasks);
		}
		
		toRun.forEach(x -> x.run());
		extendedTimer.completed();
	}
	
	public void addSubTask(Runnable subTask) {
		synchronized (subTasks) {
			subTasks.add(subTask);
		}
	}
}
