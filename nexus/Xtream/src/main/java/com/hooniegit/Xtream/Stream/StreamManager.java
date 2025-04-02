package com.hooniegit.Xtream.Stream;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * List<Stream<T>> 객체를 관리하고 Stream<T> 객체를 순차 제공하는 클래스입니다.
 * - Thread 크기를 증가시키는 대신 복수의 Stream 객체를 생성 후 개별적으로 관리합니다.
 * - Thread 크기가 1024 바이트를 초과함으로 인해 발생하는 캐시 효율성 저하 및 병목 발생 문제를 방지합니다.
 */

public class StreamManager<T> {

    private final List<Stream<T>> streamList;
    private final AtomicInteger currentIndex;

    public StreamManager(List<Stream<T>> streamList) {
        this.streamList = streamList;
        this.currentIndex = new AtomicInteger(0);
    }

    /**
     * Stream<T> 객체를 순차 반환합니다.
     */
    public Stream<T> getNextStream() {
        int index = currentIndex.getAndUpdate(i -> (i + 1) % streamList.size());
        return streamList.get(index);
    }
}
