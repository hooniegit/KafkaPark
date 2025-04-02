package com.hooniegit.Xtream.Stream;

import java.util.List;

/**
 * Stream<T> 객체를 생성하는 클래스입니다. 객체 초기화 시에 필요한 List<Handler<T>> 객체를 입력 받습니다.
 */

public class StreamBuilder<T> {
	
    private final List<Handler<T>> handlers;

    public StreamBuilder(List<Handler<T>> handlers) {
        this.handlers = handlers;
    }

    /**
     * Stream<T> 객체를 생성 후 반환합니다.
     * @return
     */
    public Stream<T> build() {
        return new Stream<T>(handlers);
    }
    
}
