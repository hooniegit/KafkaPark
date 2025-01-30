package com.hooniegit.KafkaProducer.Producer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.hooniegit.KafkaProducer.DataClass.Complexed;
import com.hooniegit.KafkaProducer.DataClass.Specified;
import com.hooniegit.KafkaProducer.Serializer.KryoSerializer;

import jakarta.annotation.PostConstruct;

@Service
public class KafkaProducerService {

    @Autowired
    private KafkaTemplate<String, byte[]> kafkaTemplate;
    
    private final Random random = new Random();

    /**
     * Service Method (for Test) :: Send Serialized Datas to Apache Kafka Broker
     */
    @PostConstruct
    private void service() {

        while(true) {

			for (int i = 1; i <= 6000; i++) {
                // Define Header
                HashMap<String, Object> m = new HashMap<>();
                m.put("timestamp", LocalDateTime.now().toString());

                // Define Body
                List<Specified> list = new ArrayList<>();

                // 10 Categories for Each List
                // 300 IDs for Each List
				for (int j = 1; j <= 10; j++) {
					int category = j + (i - 1) * 10;

                    // 30 IDs for Each Category
					for (int k = 1; k <= 30; k++) {
						int id = k + (j - 1) * 30 + (i - 1) * 300;

						list.add(new Specified(id, 
                                               category, 
                                               random.nextInt(), 
                                               null, 
                                               null, 
                                               null, 
                                               null, 
                                               null, 
                                               null, 
                                               null, 
                                               null));
					}
				}

                // Define Complexed<List<Specified>>
                Complexed<List<Specified>> outer = new Complexed<List<Specified>>(m, list);

                // Serialize & Send
                try {
                    byte[] b = KryoSerializer.<Complexed<List<Specified>>>serialize(outer);
                    sendMessage("mirror", i, b);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

			}
        }
		
    }

    /**
     * Send byte[] Data to Apache Kafka Broker
     * @param topic
     * @param partition
     * @param message
     */
    private void sendMessage(String topic, int partition, byte[] message) {
        System.out.printf("Producing message: {} to topic: {} \n", message, topic);
        kafkaTemplate.send(topic, partition, "test", message).whenComplete((result, ex) -> {
            if (ex == null) {
                System.out.printf("Message sent successfully: {} \n", result.getRecordMetadata());
            } else {
                System.out.printf("Failed to send message \n", ex);
            }
        });
    }
}
