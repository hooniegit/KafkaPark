package com.hooniegit.KafkaProducer.config;

import com.hooniegit.SourceData.Source.Body;
import com.hooniegit.SourceData.Source.Data;
import com.hooniegit.SourceData.Source.State;
import com.hooniegit.Xerializer.Serializer.KryoSerializer;
import com.hooniegit.Xtream.Stream.Event;
import com.hooniegit.Xtream.Stream.Handler;

import lombok.Getter;
import org.springframework.kafka.core.KafkaTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * Producer Handler
 * @param <T>
 */
@Getter
public class ProducerHandler extends Handler<XtreamEvent> {
    private final Random random = new Random();

    /**
     * On Event Process
     */
    @Override
    protected void process(Event<XtreamEvent> event) {
        XtreamEvent e = event.getData();
        int i = e.getIndex();
        KafkaTemplate<String, byte[]> kafkaTemplate = e.getKafkaTemplate();

        // Create Header
        HashMap<String, Object> header = new HashMap<>();
        header.put("timestamp", LocalDateTime.now().toString());

        // Create Body
        List<Body> body = new ArrayList<>();
        for (int j = 1; j <= 10; j++) {
            // Group Unit
            int group = j + (i - 1) * 10;
            for (int k = 1; k <= 30; k++) {
                // ID Unit
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

        // Create Data
        Data<List<Body>> outer = new Data<>(header, body);

        try {
            // Serialize
            byte[] b = KryoSerializer.serialize(outer);

            // Transmit & Check
            sendMessage("WAT", (i-1)%64, b, kafkaTemplate);
            System.out.println(">>>>>>>>> " + i);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Send Message to Kafka
     * @param topic
     * @param partition
     * @param message
     * @throws Exception
     */
    private void sendMessage(String topic, int partition, byte[] message, KafkaTemplate<String, byte[]> template) {
        template.send(topic, partition, "test", message).whenComplete((result, ex) -> {
            if (ex != null) {
                System.out.println("Failed to send message " + ex);
            }
        });
    }
}