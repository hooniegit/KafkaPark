package com.hooniegit.KafkaConsumer.Stream;

import java.util.List;

/**
 * Build Stream Based On List<Handlers>
 */
public class StreamBuilder<T> {
	
    private final List<Handler<T>> handlers;

    public StreamBuilder(List<Handler<T>> handlers) {
        this.handlers = handlers;
    }

    /**
     * Build Stream Based On List<Handlers>
     * @return
     */
    public Stream<T> build() {
        return new Stream<T>(handlers);
    }
    
}
