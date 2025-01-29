package com.hooniegit.KafkaConsumer.Stream;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <Super> Event Class
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class Event<T> {

	private T data;
	
	/**
	 * Clear Properties
	 */
	public void clear() {
		this.data = null;
	}

}
