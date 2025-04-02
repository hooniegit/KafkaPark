package com.hooniegit.Xtream.Stream;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Stream 아키텍처의 구성 클래스입니다. 사용자가 만든 커스텀 구성 클래스를 기반으로 Bean 등록을 수행합니다.
 */

@Configuration
public class StreamAutoConfiguration<T> {

    /**
     * 전달된 핸들러 리스트를 기반으로 StreamBuilder 객체를 생성하여 Bean으로 등록합니다.
     *
     * @param handlers 핸들러 리스트
     * @return StreamBuilder 객체
     */
    @Bean
    public StreamBuilder<T> streamBuilder(List<Handler<T>> handlers) {
        return new StreamBuilder<>(handlers);
    }

    /**
     * 전달된 StreamBuilder 객체와 Stream 사이즈를 기반으로 Stream 리스트를 생성하여 Bean으로 등록합니다.
     *
     * @param streamBuilder StreamBuilder 객체
     * @param streamSize    Stream 사이즈
     * @return List<Stream> 객체
     */
    @Bean
    public List<Stream<T>> streamList(StreamBuilder<T> streamBuilder, @Value("${LMAX.stream.size}") int streamSize) {
        List<Stream<T>> streamList = new CopyOnWriteArrayList<>();
        // List<Stream<T>> streamList = new ArrayList<>();
        for (int i = 0; i < streamSize; i++) {
            streamList.add(streamBuilder.build());
        }
        return streamList;
    }

    /**
     * 전달된 Stream 리스트를 기반으로 StreamManager 객체를 생성하여 Bean으로 등록합니다.
     *
     * @param streamList Stream 리스트
     * @return StreamManager 객체
     */
    @Bean
    public StreamManager<T> streamManager(List<Stream<T>> streamList) {
        return new StreamManager<>(streamList);
    }
    
}
