package com.hooniegit.Xtream.Tools;

import com.lmax.disruptor.EventHandler;

import lombok.Getter;

/**
 * Event Handler Class For Processing Events
 * @param <T>
 */
@Getter
public class Handler<T> implements EventHandler<Event<T>> {

    /**
     * On-Event Task Method
     */
    @Override
    public void onEvent(Event<T> event, long sequence, boolean endOfBatch) {
        process(event);    
    }

    /**
     * Actual Task Method (Need to Override This Method)
     * @param event
     */
    protected void process(Event<T> event) { }

}
