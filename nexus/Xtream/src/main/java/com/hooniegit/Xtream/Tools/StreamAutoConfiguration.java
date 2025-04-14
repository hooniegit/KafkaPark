package com.hooniegit.Xtream.Tools;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Spring Configuration for Xtream
 * @param <T>
 */
@Configuration
public class StreamAutoConfiguration<T> {

    /**
     * StreamBuilder Bean
     * @param handlers
     * @param bufferMultiplier
     * @return
     */
    @Bean
    public StreamBuilder<T> streamBuilder(List<Handler<T>> handlers, @Value("${LMAX.buffer.multiplier}") int bufferMultiplier) {
        return new StreamBuilder<>(handlers, bufferMultiplier);
    }

    /**
     * Stream List Bean
     * @param streamBuilder
     * @param streamSize
     * @return
     */
    @Bean
    public List<Stream<T>> streamList(StreamBuilder<T> streamBuilder, @Value("${LMAX.list.size}") int streamSize) {
        List<Stream<T>> streamList = new CopyOnWriteArrayList<>();
        for (int i = 0; i < streamSize; i++) {
            streamList.add(streamBuilder.build());
        }
        return streamList;
    }

    /**
     * StreamManager Bean
     * @param streamList
     * @return
     */
    @Bean
    public StreamManager<T> streamManager(List<Stream<T>> streamList) {
        return new StreamManager<>(streamList);
    }
    
}
