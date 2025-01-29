package com.hooniegit.KafkaConsumer.Stream.Configuration.Handlers;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import com.hooniegit.KafkaConsumer.Stream.Event;
import com.hooniegit.KafkaConsumer.Stream.Handler;

import lombok.Getter;

/**
 * <Sample> Custom Event Handler
 * - Run Process With Event
 * - Update Data (For Next Event)
 */
@Getter
public class EventHandler extends Handler<ConsumerRecord<String, byte[]>> {
	
	/**
	 * On Event Task
	 */
	@Override
    protected void process(Event<ConsumerRecord<String, byte[]>> event) {

    }

}
