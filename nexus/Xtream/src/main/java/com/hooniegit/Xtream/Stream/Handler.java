package com.hooniegit.Xtream.Stream;

import com.lmax.disruptor.EventHandler;

import lombok.Getter;

/**
 * 이벤트 처리를 위한 핸들러 메서드입니다. 상속 클래스 인스턴스들을 해당 클래스로 업캐스팅해 사용합니다.
 */

@Getter
public class Handler<T> implements EventHandler<Event<T>> {

    /**
     * 이벤트 처리 메서드입니다.
     */
    @Override
    public void onEvent(Event<T> event, long sequence, boolean endOfBatch) {
        process(event);    
    }

    /**
     * 이벤트 처리에서 수행할 작업을 정의하는 메서드입니다. 상속 클래스에서 해당 메서드를 @Override 하여 기능을 구현해야 합니다.
     * @param event
     */
    protected void process(Event<T> event) { }

}
