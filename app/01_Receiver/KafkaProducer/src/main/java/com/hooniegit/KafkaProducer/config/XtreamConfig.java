package com.hooniegit.KafkaProducer.config;

import com.hooniegit.Xtream.Stream.StreamAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Auto Configuration for Xtream Package
 * @param <T>
 */
@Configuration
@Import(StreamAutoConfiguration.class)
public class XtreamConfig<T> {

}