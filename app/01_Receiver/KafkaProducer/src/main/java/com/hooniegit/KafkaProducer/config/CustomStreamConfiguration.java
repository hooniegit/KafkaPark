package com.hooniegit.KafkaProducer.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hooniegit.Xtream.Stream.Handler;
import com.hooniegit.Xtream.Stream.StreamAutoConfiguration;

/**
 * Custom Xtream Configuration
 */
@Configuration
public class CustomStreamConfiguration {
    @Bean
    public List<Handler<XtreamEvent>> handlers() {
        return List.of(new ProducerHandler());
    }
    @Bean
    public StreamAutoConfiguration<XtreamEvent> streamAutoConfiguration() {
        return new StreamAutoConfiguration<>();
    }
}