package com.hooniegit.KafkaConsumer.Stream;

/**
 * Clear Event Properties
 */
public class ClearingEventHandler <T> extends Handler<T> {

	/**
	 * Event Task : Clear Event Properties
	 */
	@Override
	protected void process(Event<T> event) {
		System.out.println("EVENT CLEARED");
		event.clear();
	}


}