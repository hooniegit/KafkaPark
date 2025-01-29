package com.hooniegit.KafkaConsumer.Stream;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Manage List<Stream> For Multi-Thread LMAX Usage
 */
public class StreamManager<T> {

    private final List<Stream<T>> streamList;
    private final AtomicInteger currentIndex;

    public StreamManager(List<Stream<T>> streamList) {
        this.streamList = streamList;
        this.currentIndex = new AtomicInteger(0);
    }

    /**
     * Return Next Stream Instance
     */
    public Stream<T> getNextStream() {
        int index = currentIndex.getAndUpdate(i -> (i + 1) % streamList.size());
        return streamList.get(index);
    }
}
