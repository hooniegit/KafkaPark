package com.hooniegit.KafkaConsumer.Consumer;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.listener.ConsumerAwareRebalanceListener;
import org.springframework.stereotype.Service;

import com.hooniegit.KafkaConsumer.DataClass.CategoryClass;
import com.hooniegit.KafkaConsumer.DataClass.Complexed;
import com.hooniegit.KafkaConsumer.DataClass.IdClass;
import com.hooniegit.KafkaConsumer.DataClass.Specified;
import com.hooniegit.KafkaConsumer.Serializer.KryoSerializer;

/**
 * KafkaConsumer 서비스입니다. KafkaListener 가 수신한 데이터를 가공하고 UDP 규격으로 전송합니다.
 */

@Service
public class DefaultConsumerService implements ConsumerAwareRebalanceListener {

    /**
     * 파티션이 새로 등록되었을 때, 해당 파티션의 오프셋 정보를 초기화합니다.<br>
     * - 어플리케이션 재기동 시, 오프셋 정보의 초기화가 필요한 경우에 사용합니다.<br>
     * - 필요에 따라 서비스 동작에서 제외할 수 있습니다.
     */
    @Override
    public void onPartitionsAssigned(Consumer<?, ?> consumer, Collection<org.apache.kafka.common.TopicPartition> partitions) {
        partitions.forEach(partition -> {
            consumer.seekToEnd(List.of(new org.apache.kafka.common.TopicPartition(partition.topic(), partition.partition())));
        });
    }

    /**
     * KafkaListener 의 작업을 정의합니다.
     * @param record
     */
    private void task(ConsumerRecord<String, byte[]> record) {
        try {
            // 데이터 역직렬화
            Complexed<List<Specified>> c = KryoSerializer.deserialize(record.value());

            // 필요 속성 추출
            String timestamp = (String) c.getHeader().get("timestamp");
            List<Specified> l =  c.getBody();

            System.out.println(timestamp);

            // List<IdClass> 객체 생성
            List<IdClass> idClassList = l.stream()
                .map(specified -> new IdClass(
                    specified.getId(), 
                    specified.getValue(), 
                    specified.getStatus(),
                    timestamp))
                .collect(Collectors.toList());

            // 직렬화 및 UDP 전송
            byte[] idByteArray = KryoSerializer.serialize(idClassList);
            udp(idByteArray, 9090);

            // List<CategoryClass> 객체 생성
            List<CategoryClass> categoryClassList = l.stream()
                .collect(Collectors.toMap(
                    Specified::getCategory,
                    specified -> new CategoryClass(
                            specified.getCategory(), 
                            specified.getStatus_01(), 
                            specified.getStatus_02(), 
                            specified.getStatus_03(),
                            timestamp),
                    (existing, replacement) -> existing
                ))
                .values()
                .stream()
                .collect(Collectors.toList());

            // 직렬화 및 UDP 전송송
            byte[] categoryByteArray = KryoSerializer.serialize(categoryClassList);
            udp(categoryByteArray, 9091);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
    }

    /**
     * byte[] 데이터를 입력받아 UDP 통신으로 로컬 환경에 데이터를 전송합니다.
     * @param b
     */
    private void udp(byte[] b, int port) {
        String serverAddress = "127.0.0.1";

        try (DatagramSocket clientSocket = new DatagramSocket()) {
            InetAddress address = InetAddress.getByName(serverAddress);
            DatagramPacket packet = new DatagramPacket(b, b.length, address, port);
            clientSocket.send(packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 구독하는 파티션의 수량에 비례하여 KafkaListener 스레드를 구성합니다.<br>
     * - 스레드를 독립적으로 구성해 시스템 내에 발생하는 병목 현상을 방지할 수 있습니다.
     * @param records
     * @param consumer
     */
    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"0"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume00(ConsumerRecords<String, byte[]> records, Consumer<?, ?> consumer) {
    	
    	for (ConsumerRecord<String, byte[]> record : records) {
    		task(record);
    	}
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"1"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume01(ConsumerRecords<String, byte[]> records, Consumer<?, ?> consumer) {
    	
    	for (ConsumerRecord<String, byte[]> record : records) {
    		task(record);
    	}
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"2"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume02(ConsumerRecords<String, byte[]> records, Consumer<?, ?> consumer) {
    	
    	for (ConsumerRecord<String, byte[]> record : records) {
    		task(record);
    	}
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"3"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume03(ConsumerRecords<String, byte[]> records, Consumer<?, ?> consumer) {
    	
    	for (ConsumerRecord<String, byte[]> record : records) {
    		task(record);
    	}
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"4"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume04(ConsumerRecords<String, byte[]> records, Consumer<?, ?> consumer) {
    	
    	for (ConsumerRecord<String, byte[]> record : records) {
    		task(record);
    	}
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"5"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume05(ConsumerRecords<String, byte[]> records, Consumer<?, ?> consumer) {
    	
    	for (ConsumerRecord<String, byte[]> record : records) {
    		task(record);
    	}
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"6"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume06(ConsumerRecords<String, byte[]> records, Consumer<?, ?> consumer) {
    	
    	for (ConsumerRecord<String, byte[]> record : records) {
    		task(record);
    	}
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"7"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume07(ConsumerRecords<String, byte[]> records, Consumer<?, ?> consumer) {
    	
    	for (ConsumerRecord<String, byte[]> record : records) {
    		task(record);
    	}
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"8"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume08(ConsumerRecords<String, byte[]> records, Consumer<?, ?> consumer) {
    	
    	for (ConsumerRecord<String, byte[]> record : records) {
    		task(record);
    	}
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"9"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume09(ConsumerRecords<String, byte[]> records, Consumer<?, ?> consumer) {
    	
    	for (ConsumerRecord<String, byte[]> record : records) {
    		task(record);
    	}
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"10"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume10(ConsumerRecords<String, byte[]> records, Consumer<?, ?> consumer) {
    	
    	for (ConsumerRecord<String, byte[]> record : records) {
    		task(record);
    	}
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"11"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume11(ConsumerRecords<String, byte[]> records, Consumer<?, ?> consumer) {
    	
    	for (ConsumerRecord<String, byte[]> record : records) {
    		task(record);
    	}
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"12"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume12(ConsumerRecords<String, byte[]> records, Consumer<?, ?> consumer) {
    	
    	for (ConsumerRecord<String, byte[]> record : records) {
    		task(record);
    	}
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"13"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume13(ConsumerRecords<String, byte[]> records, Consumer<?, ?> consumer) {
    	
    	for (ConsumerRecord<String, byte[]> record : records) {
    		task(record);
    	}
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"14"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume14(ConsumerRecords<String, byte[]> records, Consumer<?, ?> consumer) {
    	
    	for (ConsumerRecord<String, byte[]> record : records) {
    		task(record);
    	}
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"15"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume15(ConsumerRecords<String, byte[]> records, Consumer<?, ?> consumer) {
    	
    	for (ConsumerRecord<String, byte[]> record : records) {
    		task(record);
    	}
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"16"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume16(ConsumerRecords<String, byte[]> records, Consumer<?, ?> consumer) {
    	
    	for (ConsumerRecord<String, byte[]> record : records) {
    		task(record);
    	}
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"17"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume17(ConsumerRecords<String, byte[]> records, Consumer<?, ?> consumer) {
    	
    	for (ConsumerRecord<String, byte[]> record : records) {
    		task(record);
    	}
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"18"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume18(ConsumerRecords<String, byte[]> records, Consumer<?, ?> consumer) {
    	
    	for (ConsumerRecord<String, byte[]> record : records) {
    		task(record);
    	}
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"19"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume19(ConsumerRecords<String, byte[]> records, Consumer<?, ?> consumer) {
    	
    	for (ConsumerRecord<String, byte[]> record : records) {
    		task(record);
    	}
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"20"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume20(ConsumerRecords<String, byte[]> records, Consumer<?, ?> consumer) {
    	
    	for (ConsumerRecord<String, byte[]> record : records) {
    		task(record);
    	}
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"21"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume21(ConsumerRecords<String, byte[]> records, Consumer<?, ?> consumer) {
    	
    	for (ConsumerRecord<String, byte[]> record : records) {
    		task(record);
    	}
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"22"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume22(ConsumerRecords<String, byte[]> records, Consumer<?, ?> consumer) {
    	
    	for (ConsumerRecord<String, byte[]> record : records) {
    		task(record);
    	}
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"23"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume23(ConsumerRecords<String, byte[]> records, Consumer<?, ?> consumer) {
    	
    	for (ConsumerRecord<String, byte[]> record : records) {
    		task(record);
    	}
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"24"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume24(ConsumerRecords<String, byte[]> records, Consumer<?, ?> consumer) {
    	
    	for (ConsumerRecord<String, byte[]> record : records) {
    		task(record);
    	}
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"25"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume25(ConsumerRecords<String, byte[]> records, Consumer<?, ?> consumer) {
    	
    	for (ConsumerRecord<String, byte[]> record : records) {
    		task(record);
    	}
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"26"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume26(ConsumerRecords<String, byte[]> records, Consumer<?, ?> consumer) {
    	
    	for (ConsumerRecord<String, byte[]> record : records) {
    		task(record);
    	}
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"27"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume27(ConsumerRecords<String, byte[]> records, Consumer<?, ?> consumer) {
    	
    	for (ConsumerRecord<String, byte[]> record : records) {
    		task(record);
    	}
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"28"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume28(ConsumerRecords<String, byte[]> records, Consumer<?, ?> consumer) {
    	
    	for (ConsumerRecord<String, byte[]> record : records) {
    		task(record);
    	}
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"29"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume29(ConsumerRecords<String, byte[]> records, Consumer<?, ?> consumer) {
    	
    	for (ConsumerRecord<String, byte[]> record : records) {
    		task(record);
    	}
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"30"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume30(ConsumerRecords<String, byte[]> records, Consumer<?, ?> consumer) {
    	
    	for (ConsumerRecord<String, byte[]> record : records) {
    		task(record);
    	}
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"31"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume31(ConsumerRecords<String, byte[]> records, Consumer<?, ?> consumer) {
    	
    	for (ConsumerRecord<String, byte[]> record : records) {
    		task(record);
    	}
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"32"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume32(ConsumerRecords<String, byte[]> records, Consumer<?, ?> consumer) {
    	
    	for (ConsumerRecord<String, byte[]> record : records) {
    		task(record);
    	}
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"33"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume33(ConsumerRecords<String, byte[]> records, Consumer<?, ?> consumer) {
    	
    	for (ConsumerRecord<String, byte[]> record : records) {
    		task(record);
    	}
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"34"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume34(ConsumerRecords<String, byte[]> records, Consumer<?, ?> consumer) {
    	
    	for (ConsumerRecord<String, byte[]> record : records) {
    		task(record);
    	}
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"35"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume35(ConsumerRecords<String, byte[]> records, Consumer<?, ?> consumer) {
    	
    	for (ConsumerRecord<String, byte[]> record : records) {
    		task(record);
    	}
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"36"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume36(ConsumerRecords<String, byte[]> records, Consumer<?, ?> consumer) {
    	
    	for (ConsumerRecord<String, byte[]> record : records) {
    		task(record);
    	}
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"37"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume37(ConsumerRecords<String, byte[]> records, Consumer<?, ?> consumer) {
    	
    	for (ConsumerRecord<String, byte[]> record : records) {
    		task(record);
    	}
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"38"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume38(ConsumerRecords<String, byte[]> records, Consumer<?, ?> consumer) {
    	
    	for (ConsumerRecord<String, byte[]> record : records) {
    		task(record);
    	}
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"39"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume39(ConsumerRecords<String, byte[]> records, Consumer<?, ?> consumer) {
    	
    	for (ConsumerRecord<String, byte[]> record : records) {
    		task(record);
    	}
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"40"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume40(ConsumerRecords<String, byte[]> records, Consumer<?, ?> consumer) {
    	
    	for (ConsumerRecord<String, byte[]> record : records) {
    		task(record);
    	}
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"41"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume41(ConsumerRecords<String, byte[]> records, Consumer<?, ?> consumer) {
    	
    	for (ConsumerRecord<String, byte[]> record : records) {
    		task(record);
    	}
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"42"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume42(ConsumerRecords<String, byte[]> records, Consumer<?, ?> consumer) {
    	
    	for (ConsumerRecord<String, byte[]> record : records) {
    		task(record);
    	}
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"43"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume43(ConsumerRecords<String, byte[]> records, Consumer<?, ?> consumer) {
    	
    	for (ConsumerRecord<String, byte[]> record : records) {
    		task(record);
    	}
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"44"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume44(ConsumerRecords<String, byte[]> records, Consumer<?, ?> consumer) {
    	
    	for (ConsumerRecord<String, byte[]> record : records) {
    		task(record);
    	}
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"45"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume45(ConsumerRecords<String, byte[]> records, Consumer<?, ?> consumer) {
    	
    	for (ConsumerRecord<String, byte[]> record : records) {
    		task(record);
    	}
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"46"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume46(ConsumerRecords<String, byte[]> records, Consumer<?, ?> consumer) {
    	
    	for (ConsumerRecord<String, byte[]> record : records) {
    		task(record);
    	}
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"47"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume47(ConsumerRecords<String, byte[]> records, Consumer<?, ?> consumer) {
    	
    	for (ConsumerRecord<String, byte[]> record : records) {
    		task(record);
    	}
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"48"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume48(ConsumerRecords<String, byte[]> records, Consumer<?, ?> consumer) {
    	
    	for (ConsumerRecord<String, byte[]> record : records) {
    		task(record);
    	}
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"49"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume49(ConsumerRecords<String, byte[]> records, Consumer<?, ?> consumer) {
    	
    	for (ConsumerRecord<String, byte[]> record : records) {
    		task(record);
    	}
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"50"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume50(ConsumerRecords<String, byte[]> records, Consumer<?, ?> consumer) {
    	
    	for (ConsumerRecord<String, byte[]> record : records) {
    		task(record);
    	}
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"51"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume51(ConsumerRecords<String, byte[]> records, Consumer<?, ?> consumer) {
    	
    	for (ConsumerRecord<String, byte[]> record : records) {
    		task(record);
    	}
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"52"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume52(ConsumerRecords<String, byte[]> records, Consumer<?, ?> consumer) {
    	
    	for (ConsumerRecord<String, byte[]> record : records) {
    		task(record);
    	}
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"53"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume53(ConsumerRecords<String, byte[]> records, Consumer<?, ?> consumer) {
    	
    	for (ConsumerRecord<String, byte[]> record : records) {
    		task(record);
    	}
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"54"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume54(ConsumerRecords<String, byte[]> records, Consumer<?, ?> consumer) {
    	
    	for (ConsumerRecord<String, byte[]> record : records) {
    		task(record);
    	}
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"55"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume55(ConsumerRecords<String, byte[]> records, Consumer<?, ?> consumer) {
    	
    	for (ConsumerRecord<String, byte[]> record : records) {
    		task(record);
    	}
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"56"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume56(ConsumerRecords<String, byte[]> records, Consumer<?, ?> consumer) {
    	
    	for (ConsumerRecord<String, byte[]> record : records) {
    		task(record);
    	}
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"57"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume57(ConsumerRecords<String, byte[]> records, Consumer<?, ?> consumer) {
    	
    	for (ConsumerRecord<String, byte[]> record : records) {
    		task(record);
    	}
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"58"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume58(ConsumerRecords<String, byte[]> records, Consumer<?, ?> consumer) {
    	
    	for (ConsumerRecord<String, byte[]> record : records) {
    		task(record);
    	}
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"59"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume59(ConsumerRecords<String, byte[]> records, Consumer<?, ?> consumer) {
    	
    	for (ConsumerRecord<String, byte[]> record : records) {
    		task(record);
    	}
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"60"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume60(ConsumerRecords<String, byte[]> records, Consumer<?, ?> consumer) {
    	
    	for (ConsumerRecord<String, byte[]> record : records) {
    		task(record);
    	}
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"61"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume61(ConsumerRecords<String, byte[]> records, Consumer<?, ?> consumer) {
    	
    	for (ConsumerRecord<String, byte[]> record : records) {
    		task(record);
    	}
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"62"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume62(ConsumerRecords<String, byte[]> records, Consumer<?, ?> consumer) {
    	
    	for (ConsumerRecord<String, byte[]> record : records) {
    		task(record);
    	}
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"63"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume63(ConsumerRecords<String, byte[]> records, Consumer<?, ?> consumer) {
    	
    	for (ConsumerRecord<String, byte[]> record : records) {
    		task(record);
    	}
    }
	
}
