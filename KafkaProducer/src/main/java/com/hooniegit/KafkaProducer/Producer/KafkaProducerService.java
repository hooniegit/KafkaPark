package com.hooniegit.KafkaProducer.Producer;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.hooniegit.KafkaProducer.DataClass.Complexed;
import com.hooniegit.KafkaProducer.DataClass.Specified;
import com.hooniegit.KafkaProducer.DataClass.State;
import com.hooniegit.KafkaProducer.Serializer.KryoSerializer;

import jakarta.annotation.PostConstruct;

@Service
public class KafkaProducerService {

    @Autowired
    private KafkaTemplate<String, byte[]> kafkaTemplate;
    
    private final Random random = new Random();

    private State[] state = {State.UNKNOWN, State.STOPPED, State.RUNNING, State.PROBLEM};

    /**
     * Service Method (for Test) :: Send Serialized Datas to Apache Kafka Broker
     */
    @PostConstruct
    private void service() {

        while(true) {

            int minute = Integer.parseInt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM")));

			for (int i = 1; i <= 6000; i++) {
 
                // Create Header
                HashMap<String, Object> header = new HashMap<>();
                header.put("timestamp", LocalDateTime.now().toString());

                // Create Body
                List<Specified> body = new ArrayList<>();
				for (int j = 1; j <= 10; j++) {
					int category = j + (i - 1) * 10; // category: 1 ~ 60,000
					for (int k = 1; k <= 30; k++) {
						int id = k + (j - 1) * 30 + (i - 1) * 300; // id: 1 ~ 1,800,000
						body.add(new Specified(id, 
                                               random.nextInt(), 
                                               null,
                                               category, 
                                               this.state[minute%4].getInfo(), // change every minute
                                               this.state[(minute%4 + 1)%4].getInfo(),
                                               this.state[(minute%4 + 2)%4].getInfo()));
					}
				}

                // Create Complexed
                Complexed<List<Specified>> outer = new Complexed<>(header, body);

                // Serialize & Send
                try {
                    byte[] b = KryoSerializer.serialize(outer);
                    sendMessage(null, i, b);
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

    /**
     * [TEST] Send byte[] Data to Localhost
     * @param b
     */
    private void udp(byte[] b) {
        String serverAddress = "127.0.0.1";
        int port = 12345;

        try (DatagramSocket clientSocket = new DatagramSocket()) {
            InetAddress address = InetAddress.getByName(serverAddress);
            DatagramPacket packet = new DatagramPacket(b, b.length, address, port);
            clientSocket.send(packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
