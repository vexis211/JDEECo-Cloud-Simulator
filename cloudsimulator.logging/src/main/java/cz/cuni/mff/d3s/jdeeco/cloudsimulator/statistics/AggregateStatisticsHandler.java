package cz.cuni.mff.d3s.jdeeco.cloudsimulator.statistics;

import java.util.ArrayList;
import java.util.List;

public class AggregateStatisticsHandler implements StatisticsHandler {

	private final List<StatisticsHandler> handlers = new ArrayList<>();

	public void addHandler(StatisticsHandler handler) {
		synchronized (handlers) {
			handlers.add(handler);
		}
	}

	public void removeHandler(StatisticsHandler handler) {
		synchronized (handlers) {
			handlers.remove(handler);
		}
	}

	@Override
	public void write(String id, boolean value) {
		synchronized (handlers) {
			for (StatisticsHandler handler : handlers) {
				handler.write(id, value);
			}
		}
	}

	@Override
	public void write(String id, byte value) {
		synchronized (handlers) {
			for (StatisticsHandler handler : handlers) {
				handler.write(id, value);
			}
		}
	}

	@Override
	public void write(String id, short value) {
		synchronized (handlers) {
			for (StatisticsHandler handler : handlers) {
				handler.write(id, value);
			}
		}
	}

	@Override
	public void write(String id, int value) {
		synchronized (handlers) {
			for (StatisticsHandler handler : handlers) {
				handler.write(id, value);
			}
		}
	}

	@Override
	public void write(String id, float value) {
		synchronized (handlers) {
			for (StatisticsHandler handler : handlers) {
				handler.write(id, value);
			}
		}
	}

	@Override
	public void write(String id, double value) {
		synchronized (handlers) {
			for (StatisticsHandler handler : handlers) {
				handler.write(id, value);
			}
		}
	}
}
