package com.hooniegit.KafkaProducer.Producer;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

// Nexus Dependencies
import com.hooniegit.SourceData.Source.Body;
import com.hooniegit.SourceData.Source.Data;
import com.hooniegit.SourceData.Source.State;
import com.hooniegit.Xerializer.Kryo.KryoSerializer;

/**
 * Kafka Producer Service
 * - Create Data Instance
 * - Serialize Data (Based on Kryo)
 * - Transmit Data to Kafka Broker
 */
@Service
public class KafkaProducerService {
    @Autowired
    private KafkaTemplate<String, byte[]> kafkaTemplate;
    private final Random random = new Random();

    /**
     * Post Construct Task
     */
    @PostConstruct
    private void service() throws Exception {
        int cnt = 0;

        Time start = new Time(System.currentTimeMillis());

        outer: while(true) {
            for (int i = 1; i <= 6000; i++) {
                // Generate Header
                HashMap<String, Object> header = new HashMap<>();
                header.put("timestamp", LocalDateTime.now());

                // Generate Body
                List<Body> body = new ArrayList<>();
                for (int j = 1; j <= 10; j++) {
                    int group = j + (i - 1) * 10;
                    for (int k = 1; k <= 30; k++) {
                        int id = k + (j - 1) * 30 + (i - 1) * 300;
                        body.add(new Body(id,
                                random.nextInt(),
                                true,
                                group,
                                State.RUNNING,
                                null,
                                null));
                    }
                }

                // Generate Data
                Data<List<Body>> outer = new Data<>(header, body);

                try {
                    // Serialize Data & Send to Kafka
                    byte[] b = KryoSerializer.serialize(outer);
                    sendMessage("WAT", (i-1)%64, b);
                    System.out.println("Kafka Producer Service - Publish Event : " + ++cnt);
//                    if (cnt == 50000) {
//                        Time end = new Time(System.currentTimeMillis());
//                        System.out.println("Kafka Producer Service - Elapsed Time : " + (end.getTime() - start.getTime()) + "ms");
//                        break outer;
//                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    /**
     * Send Message to Kafka
     * @param topic
     * @param partition
     * @param message
     * @throws Exception
     */
    private void sendMessage(String topic, int partition, byte[] message) {
        kafkaTemplate.send(topic, partition, "test", message).whenComplete((result, ex) -> {
            if (ex != null) {
                System.out.println("Failed to send message " + ex);
            }
        });
    }
}
