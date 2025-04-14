package com.hooniegit.Xtream.Tools;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * StreamManager Class for Managing Stream List and Providing Stream Sequentially
 * @param <T>
 */
public class StreamManager<T> {

    private final List<Stream<T>> streamList;
    private final AtomicInteger currentIndex;

    public StreamManager(List<Stream<T>> streamList) {
        this.streamList = streamList;
        this.currentIndex = new AtomicInteger(0);
    }

    /**
     * Provide Stream Sequentially
     */
    public Stream<T> getNextStream() {
        int index = currentIndex.getAndUpdate(i -> (i + 1) % streamList.size());
        return streamList.get(index);
    }
}
