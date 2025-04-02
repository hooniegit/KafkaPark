package com.hooniegit.Xtream.Stream;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 이벤트 클래스입니다. 처리가 완료된 이벤트는 `ClearingEventHandler` 에서 데이터를 초기화합니다.
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class Event<T> {

	private T data;

	public void clear() {
		this.data = null;
	}

}
