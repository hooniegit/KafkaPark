package com.hooniegit.KafkaProducer.Producer;

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
import com.hooniegit.SourceData.Source.Complexed;
import com.hooniegit.SourceData.Source.Specified;
import com.hooniegit.SourceData.Source.State;
import com.hooniegit.Xerializer.Serializer.KryoSerializer;

/**
 * Kafka Producer 서비스입니다. 더미 데이터를 대량으로 생성하여 연속 발행합니다.<br>
 * - KafkaProducerConfig 클래스를 기반으로 동작합니다.
 */

@Service
public class KafkaProducerService {

    @Autowired
    private KafkaTemplate<String, byte[]> kafkaTemplate;
    private final Random random = new Random();

    /**
     * 데이터를 발행하는 @PostConstruct 서비스입니다.
     */
    @PostConstruct
    private void service() {

        while(true) {

			for (int i = 1; i <= 6000; i++) {
 
                // Header 생성
                HashMap<String, Object> header = new HashMap<>();
                header.put("timestamp", LocalDateTime.now().toString());

                // Body 생성
                List<Specified> body = new ArrayList<>();

                // group 단위 : 1 ~ 60,000
				for (int j = 1; j <= 10; j++) {
					int group = j + (i - 1) * 10;

                    // id 단위 : 1 ~ 1,800,000
					for (int k = 1; k <= 30; k++) {
						int id = k + (j - 1) * 30 + (i - 1) * 300;
						body.add(new Specified(id, 
                                               random.nextInt(), 
                                               true,
                                               group, 
                                               State.RUNNING,
                                               null,
                                               null));
					}
				}

                // Complexed 객체 생성
                Complexed<List<Specified>> outer = new Complexed<>(header, body);

                // 직렬화 및 데이터 전송
                try {
                    byte[] b = KryoSerializer.serialize(outer);
                    sendMessage("WAT", (i-1)%64, b);
                    System.out.println(">>>>>>>>> " + i);

                    // Complexed<List<Specified>> c = (Complexed<List<Specified>>) KryoSerializer.deserialize(b);
                    // System.out.println(c.getHeader().get("timestamp"));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

			}
        }
		
    }

    /**
     * byte[] 데이터를 입력받아 메타 정보와 함께 브로커에 전송합니다. 브로커는 전송받은 데이터를 토픽에 발행합니다.
     * @param topic
     * @param partition
     * @param message
     */
    private void sendMessage(String topic, int partition, byte[] message) {
        kafkaTemplate.send(topic, partition, "test", message).whenComplete((result, ex) -> {
            if (ex == null) {
                System.out.println("Message sent successfully to.. " + partition);
            } else {
                System.out.println("Failed to send message " + ex);
            }
        });
    }

}
