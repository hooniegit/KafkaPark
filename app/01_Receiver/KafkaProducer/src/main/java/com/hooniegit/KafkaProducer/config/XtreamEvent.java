package com.hooniegit.KafkaProducer.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.kafka.core.KafkaTemplate;

/**
 * Xtream Event Data Class
 */
@Getter @AllArgsConstructor
public class XtreamEvent {
    private KafkaTemplate<String, byte[]> kafkaTemplate;
    private int index;
}
