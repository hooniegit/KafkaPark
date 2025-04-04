package com.hooniegit.KafkaConsumer.Consumer;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.listener.ConsumerSeekAware;
import org.springframework.stereotype.Service;

// Nexus Dependencies
import com.hooniegit.SourceData.Source.Data;
import com.hooniegit.SourceData.Source.Body;
import com.hooniegit.SourceData.Interface.Package;
import com.hooniegit.SourceData.Interface.TagData;
import com.hooniegit.SourceData.Interface.TagGroup;
import com.hooniegit.Xerializer.Serializer.KryoSerializer;

/**
 * KafkaConsumer 서비스입니다. KafkaListener 측에서 수신하는 데이터를 가공하고 UDP 규격으로 전송합니다.
 */

@Service
public class DefaultConsumerService implements ConsumerSeekAware {

    /**
     * 파티션이 새로 등록되었을 때, 해당 파티션의 오프셋 정보를 초기화합니다.
     */
    @Override
    public void onPartitionsAssigned(Map<org.apache.kafka.common.TopicPartition, Long> assignments, ConsumerSeekCallback callback) {

        assignments.keySet().forEach(partition -> {
        callback.seekToEnd("WAT", partition.partition());
        });

    }

    /**
     * KafkaListener 의 작업을 정의합니다.
     * @param record
     */
    private void task(ConsumerRecord<String, byte[]> record) {

        try {
            // 데이터 역직렬화
            Data<List<Body>> c = KryoSerializer.deserialize(record.value());

            // 필요 속성 추출
            String timestamp = (String) c.getHeader().get("timestamp");
            List<Body> l =  c.getBody();

            Package pkg = generatePackage(l, timestamp);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
    }

    /**
     * TotalPackage 객체를 생성합니다.
     * @param l
     * @param datetime
     * @return
     */
    private Package generatePackage(List<Body> list, String timestamp) {

        // List 객체 생성
        List<TagData<Double>> values = new ArrayList<>();
        List<TagData<Boolean>> modes = new ArrayList<>();
        List<TagData<Integer>> states = new ArrayList<>();
        List<TagData<String>> statusOnes = new ArrayList<>();
        List<TagData<String>> statusTwos = new ArrayList<>();

        // group 값이 변경되면 해당 group의 상태 저장
        int prevGroup = 0;

        // List 데이터 추가
        for (Body b : list) {
            // tag 단위 데이터 추가
            int id = b.getId();
            values.add(
                new TagData<Double>(
                    id, 
                    b.getValue()
                ));
            modes.add(
                new TagData<Boolean>(
                    id, 
                    b.isMode()
                ));

            // group 단위 데이터 추가
            int currentGroup = b.getGroup();
            if (currentGroup != prevGroup) {
                states.add(
                    new TagData<Integer>(
                        currentGroup,
                        b.getState().getValue()
                    )
                );
                statusOnes.add(
                    new TagData<String>(
                        currentGroup,
                        b.getStatusOne()
                    )
                );
                statusTwos.add(
                    new TagData<String>(
                        currentGroup,
                        b.getStatusTwo()
                    )
                );
                prevGroup = currentGroup;
            }
        }

        // TotalPackage 객체 생성 및 반환
        return new Package(
            new TagGroup<Double>(timestamp, values),
            new TagGroup<Boolean>(timestamp, modes),
            new TagGroup<Integer>(timestamp, states),
            new TagGroup<String>(timestamp, statusOnes),
            new TagGroup<String>(timestamp, statusTwos)
        );

    }

    /**
     * byte[] 데이터를 입력받아 UDP 통신으로 로컬 환경에 데이터를 전송합니다.
     * @param b
     */
    private void udp(byte[] b, int port) {

        try (DatagramSocket clientSocket = new DatagramSocket()) {
            InetAddress address = InetAddress.getByName("127.0.0.1");
            DatagramPacket packet = new DatagramPacket(b, b.length, address, port);
            clientSocket.send(packet);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 구독하는 파티션의 수량에 비례하여 KafkaListener 스레드를 구성합니다.
     *  * 스레드를 독립적으로 구성해 시스템 내에 발생하는 병목 현상을 방지할 수 있습니다.
     * @param record
     * @param consumer
     */
    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"0"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume00(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) {

    	task(record);

    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"1"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume01(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) {
    	
    	task(record);

    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"2"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume02(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) {

    	task(record);

    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"3"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume03(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) {

    	task(record);

    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"4"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume04(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) {

    	task(record);

    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"5"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume05(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) {

    	task(record);

    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"6"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume06(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) {
    	
    	task(record);

    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"7"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume07(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) {
    	
    	task(record);

    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"8"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume08(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) {
    	
    	task(record);

    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"9"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume09(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) {
    	
    	task(record);

    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"10"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume10(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) {
    	
    	task(record);

    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"11"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume11(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) {
    	
    	task(record);

    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"12"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume12(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) {

    	task(record);

    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"13"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume13(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) {
    	
    	task(record);

    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"14"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume14(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) {
    	
    	task(record);

    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"15"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume15(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) {
    	
    	task(record);

    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"16"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume16(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) {
    	
    	task(record);

    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"17"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume17(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) {
    	
    	task(record);

    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"18"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume18(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) {
    	
    	task(record);

    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"19"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume19(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) {
    	
    	task(record);

    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"20"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume20(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) {

    	task(record);

    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"21"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume21(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) {

    	task(record);

    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"22"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume22(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) {

    	task(record);

    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"23"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume23(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) {

    	task(record);

    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"24"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume24(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) {

    	task(record);

    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"25"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume25(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) {

    	task(record);

    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"26"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume26(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) {

    	task(record);

    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"27"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume27(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) {

    	task(record);

    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"28"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume28(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) {

    	task(record);

    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"29"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume29(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) {
    	
    	task(record);

    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"30"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume30(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) {
    	
    	task(record);

    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"31"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume31(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) {

    	task(record);

    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"32"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume32(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) {
    	
    	task(record);

    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"33"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume33(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) {
    	
    	task(record);

    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"34"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume34(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) {
    	
    	task(record);

    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"35"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume35(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) {
    	
    	task(record);

    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"36"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume36(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) {
    	
    	task(record);

    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"37"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume37(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) {
    	
    	task(record);

    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"38"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume38(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) {
    	
    	task(record);

    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"39"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume39(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) {
    	
    	task(record);

    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"40"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume40(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) {

    	task(record);

    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"41"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume41(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) {

    	task(record);

    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"42"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume42(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) {

    	task(record);

    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"43"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume43(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) {

    	task(record);

    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"44"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume44(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) {

    	task(record);

    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"45"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume45(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) {

    	task(record);

    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"46"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume46(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) {

    	task(record);

    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"47"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume47(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) {

    	task(record);

    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"48"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume48(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) {

    	task(record);

    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"49"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume49(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) {

    	task(record);

    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"50"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume50(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) {

    	task(record);

    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"51"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume51(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) {

    	task(record);

    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"52"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume52(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) {

    	task(record);

    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"53"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume53(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) {

    	task(record);

    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"54"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume54(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) {

    	task(record);

    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"55"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume55(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) {

    	task(record);

    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"56"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume56(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) {

    	task(record);

    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"57"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume57(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) {
    	
    	task(record);

    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"58"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume58(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) {

    	task(record);

    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"59"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume59(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) {

    	task(record);

    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"60"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume60(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) {

    	task(record);

    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"61"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume61(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) {

    	task(record);

    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"62"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume62(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) {
    	
    	task(record);

    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"63"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume63(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) {
    	
    	task(record);

    }
	
}
