package cz.cuni.mff.d3s.jdeeco.cloudsimulator.common;

import java.util.Timer;

public class ExtendedTimer {
	private final Timer timer = new Timer();
	private final ExtendedTimerTask timerTask;
	private long milliseconds = 0;
	private boolean autoReset = false;
	private final Object locker = new Object();

	public ExtendedTimer() {
		timerTask = new ExtendedTimerTask(this);
	}

	public void setInterval(long milliseconds) {
		this.milliseconds = milliseconds;
	}

	public void setAutoReset(boolean autoReset) {
		this.autoReset = autoReset;
	}

	public void addTask(final Runnable runnable) {
		timerTask.addSubTask(runnable);
	}

	public void start() {
		synchronized (locker) {
			timerTask.cancel();
			timer.schedule(timerTask, milliseconds);
		}
	}

	public void stop() {
		synchronized (locker) {
			timerTask.cancel();
		}
	}
	
	public void reset() {
		synchronized (locker) {
			timerTask.cancel();
			timer.schedule(timerTask, milliseconds);
		}
	}

	public void completed() {
		if (autoReset) {
			start();
		}
	}
}
