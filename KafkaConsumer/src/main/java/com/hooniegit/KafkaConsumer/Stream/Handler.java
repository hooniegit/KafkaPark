package com.hooniegit.KafkaConsumer.Stream;

import com.lmax.disruptor.EventHandler;

import lombok.Getter;

/**
 * Event Handler Based On EventHandler<T>
 * 
 */
@Getter
public class Handler<T> implements EventHandler<Event<T>> {

    /**
     * On Event Task
     */
    @Override
    public void onEvent(Event<T> event, long sequence, boolean endOfBatch) {
        process(event);    
    }

    /**
     * On Event Process : NEED TO OVERRIDE
     * @param event
     */
    protected void process(Event<T> event) {
    	// ** Override This Method **
        // Need to Define Tasks & Sharing (At Extend Class)
    }

}
