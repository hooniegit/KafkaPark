package com.hooniegit.Xtream.Tools;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Event Class
 * @param <T>
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class Event<T> {

	private T data;

	/**
	 * Clear The Data (After Processing)
	 */
	public void clear() {
		this.data = null;
	}

}
