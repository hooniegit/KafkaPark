package com.hooniegit.KafkaProducer.Producer;

import java.sql.Time;
import java.util.Random;

import com.hooniegit.KafkaProducer.config.XtreamEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

// Nexus Dependencies
import com.hooniegit.Xtream.Stream.StreamManager;

/**
 * Kafka Producer Service
 * - Publish Event Based On Xtream
 *     - Create Data Instance
 *     - Serialize Data (Based on Kryo)
 *     - Transmit Data to Kafka Broker
 */
@Service
public class KafkaProducerService {
    @Autowired
    private KafkaTemplate<String, byte[]> kafkaTemplate;
    private final StreamManager<XtreamEvent> manager;
    private final Random random = new Random();

    @Autowired
    public KafkaProducerService(StreamManager<XtreamEvent> manager) {
        this.manager = manager;
    }

    /**
     * Post Construct Task
     */
    @PostConstruct
    private void service() {

        Time start = new Time(System.currentTimeMillis());

        int cnt = 0;

        // Repeat
        outer: while(true) {
            for (int i = 1; i <= 6000; i++) {
                this.manager.getNextStream().publishInitialEvent(new XtreamEvent(kafkaTemplate, i));
                // System.out.println("Kafka Producer Service - Publish Event : " + ++cnt);
                cnt++;
                if (cnt == 50000) {
                    Time end = new Time(System.currentTimeMillis());
                    System.out.println("Kafka Producer Service - Elapsed Time : " + (end.getTime() - start.getTime()) + "ms");
                    break outer;
                }
            }
        }
    }

}
