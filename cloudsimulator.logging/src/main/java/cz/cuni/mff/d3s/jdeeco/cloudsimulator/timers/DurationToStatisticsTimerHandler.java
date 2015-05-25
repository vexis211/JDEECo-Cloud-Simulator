package cz.cuni.mff.d3s.jdeeco.cloudsimulator.timers;

import java.util.concurrent.ConcurrentHashMap;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.statistics.Statistics;

public class DurationToStatisticsTimerHandler implements TimerHandler {

	private final ConcurrentHashMap<String, Entry> times = new ConcurrentHashMap<>();

	@Override
	public void start(String id) {
		Entry entry;
		if (times.containsKey(id)) {
			entry = times.get(id);
		} else {
			entry = new Entry(id);
		}
		entry.start();
	}

	@Override
	public void stop(String id) {
		if (times.containsKey(id)) {
			Entry entry = times.get(id);
			entry.stop();
		}
	}

	@Override
	public void reset(String id) {
		if (times.containsKey(id)) {
			Entry entry = times.get(id);
			entry.reset();
		}
	}

	private class Entry {
		private String id;
		private final ConcurrentHashMap<Long, Long> timesByThreadId = new ConcurrentHashMap<>();

		public Entry(String id) {
			this.id = id;
		}

		public void start() {
			long threadId = Thread.currentThread().getId();
			if (timesByThreadId.containsKey(threadId)) {
				throw new TimerException(
						String.format(
								"Cannot run two timer with the same id in the same thread at the same time. Timer ID: '%s', thread ID: '%d'.",
								id, threadId));
			}
			timesByThreadId.put(threadId, System.nanoTime());
		}

		public void stop() {
			long threadId = Thread.currentThread().getId();
			if (timesByThreadId.containsKey(threadId)) {
				long endTime = System.nanoTime();
				long startTime = timesByThreadId.remove(threadId);

				Statistics.Write(id, endTime - startTime);
			}
		}

		public void reset() {
			long threadId = Thread.currentThread().getId();
			timesByThreadId.remove(threadId);
		}
	}
}
