package cz.cuni.mff.d3s.jdeeco.cloudsimulator.asserts;

import java.util.ArrayList;
import java.util.List;

public class AggregateAssertHandler implements AssertHandler {

	private final List<AssertHandler> handlers = new ArrayList<>();

	public void addHandler(AssertHandler handler) {
		synchronized (handlers) {
			handlers.add(handler);
		}
	}

	public void removeHandler(AssertHandler handler) {
		synchronized (handlers) {
			handlers.remove(handler);
		}
	}

	@Override
	public void fail(String message, String assertionGroup) {
		synchronized (handlers) {
			for (AssertHandler handler : handlers) {
				handler.fail(message, assertionGroup);
			}
		}
	}

	@Override
	public void success(String message, String assertionGroup) {
		synchronized (handlers) {
			for (AssertHandler handler : handlers) {
				handler.success(message, assertionGroup);
			}
		}
	}
}
