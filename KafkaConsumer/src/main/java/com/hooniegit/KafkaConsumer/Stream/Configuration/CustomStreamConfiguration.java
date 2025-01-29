package com.hooniegit.KafkaConsumer.Stream.Configuration;

import java.util.List;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hooniegit.KafkaConsumer.Stream.Handler;
import com.hooniegit.KafkaConsumer.Stream.StreamAutoConfiguration;
import com.hooniegit.KafkaConsumer.Stream.Configuration.Handlers.EventHandler;

/**
 * 사용자가 별도로 구성해야 하는 구성 클래스입니다. 시스템 구축을 위해 이하의 작업을 수행해야 합니다.
 * - 이벤트가 처리해야 할 데이터 타입 및 핸들러 구성
 * - handlers() 메서드의 데이터 타입 수정 및 핸들러 리스트 추가
 */

@Configuration
public class CustomStreamConfiguration {

    /**
     * Sample 데이터 타입의 핸들러를 생성하여 Bean 환경에 등록합니다.
     *
     * @return 핸들러 리스트
     */
    @Bean
    public List<Handler<ConsumerRecord<String, byte[]>>> handlers() {
        return List.of(new EventHandler());
    }

    /**
     * StreamAutoConfiguration을 구성하고 Bean 환경에 등록합니다.
     */
    @Bean
    public StreamAutoConfiguration<ConsumerRecord<String, byte[]>> streamAutoConfiguration() {
        return new StreamAutoConfiguration<>();
    }

}

