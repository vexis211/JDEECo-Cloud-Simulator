package cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class ExtendedBlockingQueue<T> extends LinkedBlockingQueue<T> {

	private static final long serialVersionUID = -5971796952984667680L;

	public List<T> takeAll() throws InterruptedException {

		// take first if there is no update wait
		T firstItem = this.take();

		List<T> items = new ArrayList<T>();
		items.add(firstItem);

		T nextItem;
		while ((nextItem = this.poll()) != null) {
			items.add(nextItem);
		}

		return items;
	}

	public List<T> takeAll(long timeout, TimeUnit unit) throws InterruptedException {

		// take first if there is no update wait
		T firstItem = this.poll(timeout, unit);
		List<T> items = new ArrayList<T>();

		if (firstItem != null) {
			items.add(firstItem);

			T nextItem;
			while ((nextItem = this.poll()) != null) {
				items.add(nextItem);
			}
		}

		return items;
	}
}
