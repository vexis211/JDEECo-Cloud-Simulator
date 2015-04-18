package cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.data;

import java.util.concurrent.TimeUnit;

public class TimeSpan {

	private TimeUnit unit;
	private long numberOUnits;

	public TimeSpan(TimeUnit unit, long numberOUnits) {
		this.unit = unit;
		this.numberOUnits = numberOUnits;
	}

	public TimeUnit getUnit() {
		return unit;
	}

	public long getNumberOUnits() {
		return numberOUnits;
	}

	public long getTotalMilliseconds() {
		return unit.toMillis(numberOUnits);
	}
}
