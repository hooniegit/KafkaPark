package com.hooniegit.Xtream.Tools;

/**
 * ClearingEventHandler Class For Event Initialization
 * @param <T>
 */
public class ClearingEventHandler<T> extends Handler<T> {

	/**
	 * Clear The Event Object (After Processing)
	 * @param event
	 */
	@Override
	protected void process(Event<T> event) {
		event.clear();
	}


}