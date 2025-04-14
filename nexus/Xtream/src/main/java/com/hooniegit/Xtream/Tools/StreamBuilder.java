package com.hooniegit.Xtream.Tools;

import java.util.List;

/**
 * StreamBuilder Class for Creating Stream Objects
 * @param <T>
 */
public class StreamBuilder<T> {
	
    private final List<Handler<T>> handlers;
    private final int bufferMultiplier;

    public StreamBuilder(List<Handler<T>> handlers, int bufferMultiplier) {
        this.handlers = handlers;
        this.bufferMultiplier = bufferMultiplier;
    }

    /**
     * Create Stream Object
     * @return
     */
    public Stream<T> build() {
        return new Stream<T>(handlers, bufferMultiplier);
    }
    
}
