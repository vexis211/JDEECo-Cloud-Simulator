package cz.cuni.mff.d3s.jdeeco.cloudsimulator.servers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExtendedBlockingQueue<T> extends LinkedBlockingQueue<T> {

	private static final long serialVersionUID = -5971796952984667680L;

	private static Logger logger = LoggerFactory.getLogger(ExtendedBlockingQueue.class);

	public List<T> takeAll() throws InterruptedException {

		logger.trace("Taking all current items from queue. If there is none, waiting until some item is added.");
		
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

		logger.trace("Taking all current items from queue. If there is none, waiting until some item is added or max {} of {}.", timeout, unit);
		
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
