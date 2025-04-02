package com.hooniegit.Xtream.Stream;

/**
 * 이벤트 객체를 초기화하는 핸들러입니다. 처리가 완료된 이벤트 객체를 초기화합니다.
 */

public class ClearingEventHandler<T> extends Handler<T> {

	@Override
	protected void process(Event<T> event) {
		event.clear();
	}


}