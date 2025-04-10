package com.hooniegit.KafkaConsumer.Consumer;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.listener.ConsumerSeekAware;
import org.springframework.stereotype.Service;

import com.hooniegit.KafkaConsumer.MSSQL.Reference;

// Nexus Dependencies
import com.hooniegit.SourceData.Source.Data;
import com.hooniegit.SourceData.Source.Body;
import com.hooniegit.SourceData.Interface.Package;
import com.hooniegit.SourceData.Interface.TagData;
import com.hooniegit.SourceData.Interface.TagGroup;
import com.hooniegit.Xerializer.Serializer.KryoSerializer;

/**
 * Default Kafka Consumer Service.
 * - 64 Partition Structure
 * - De-Serialize ConsumerRecords Value
 * - Re-Factor Data Structure
 * - Transport to Micro Services based on UDP Transportation
 */
@Service
public class DefaultConsumerService implements ConsumerSeekAware {
    private DatagramSocket clientSocket;
    private final InetAddress[] addresses = new InetAddress[4];
    private boolean initialized = false;

    @Autowired
    private Reference referenceMap;


    // Utils //////////////////////////////////////////////////////////////////////////////


    /**
     * Re-Set Offset Data to Newest.
     */
    @Override
    public void onPartitionsAssigned(Map<org.apache.kafka.common.TopicPartition, Long> assignments, ConsumerSeekCallback callback) {
        assignments.keySet().forEach(partition -> {
        callback.seekToEnd("WAT", partition.partition());
        });
    }

    /**
     * Consumer Task Method.
     * @param record
     */
    private void task(ConsumerRecord<String, byte[]> record) throws Exception {
        // Set-Up Socket
        if (!initialized) { setup(); }

        // De-Serialize
        Data<List<Body>> c = KryoSerializer.deserialize(record.value());

        // Re-Factor
        Package pkg = generatePackage(c.getBody(), (String) c.getHeader().get("timestamp"));

        // Transport
        udp(KryoSerializer.serialize(pkg.getValue()), addresses[0], 8000);
        udp(KryoSerializer.serialize(pkg.getMode()), addresses[0], 8001);
        udp(KryoSerializer.serialize(pkg.getState()), addresses[0], 8002);
        udp(KryoSerializer.serialize(pkg.getStatusOne()), addresses[0], 8003);
        udp(KryoSerializer.serialize(pkg.getStatusTwo()), addresses[0], 8004);
    }

    /**
     * Set-Up Socket.
     * @throws Exception
     */
    private void setup() throws Exception {
        // Add Socket Address Here
        this.addresses[0] = InetAddress.getByName("localhost");

        // Define Socket
        this.clientSocket = new DatagramSocket();
        this.initialized = true;
    }

    /**
     * Generate Package Data(Based On De-Serialized Data).
     * @param list
     * @param timestamp
     * @return package
     */
    private Package generatePackage(List<Body> list, String timestamp) {
        // Define List Datas
        List<TagData<Double>> values = new ArrayList<>();
        List<TagData<Boolean>> modes = new ArrayList<>();
        List<TagData<Integer>> states = new ArrayList<>();
        List<TagData<String>> statusOnes = new ArrayList<>();
        List<TagData<String>> statusTwos = new ArrayList<>();

        // for Group Data Check
        int prevGroup = 0;

        for (Body b : list) {
            // Generate Tag Unit Datas
            int id = b.getId();
            if (referenceMap.getIds().containsKey(id)) {
                int index = referenceMap.getIds().get(id);
                values.add( new TagData<Double>(index, b.getValue()) );
                modes.add( new TagData<Boolean>(index, b.isMode()) );
            }

            // Generate Group Unit Datas
            int currentGroup = b.getGroup();
            if (currentGroup != prevGroup) {
                if (referenceMap.getGroups().containsKey(currentGroup)) {
                    Integer[] indexes = referenceMap.getGroups().get(currentGroup);
                    states.add( new TagData<Integer>(indexes[0], b.getState().getValue()) );
                    statusOnes.add( new TagData<String>(indexes[1], b.getStatusOne()) );
                    statusTwos.add( new TagData<String>(indexes[2], b.getStatusTwo()) );

                    // Check New Group ID
                    prevGroup = currentGroup;
                }
            }
        }

        // Return New Package Data
        return new Package(
            new TagGroup<Double>(timestamp, values),
            new TagGroup<Boolean>(timestamp, modes),
            new TagGroup<Integer>(timestamp, states),
            new TagGroup<String>(timestamp, statusOnes),
            new TagGroup<String>(timestamp, statusTwos)
        );
    }

    /**
     * Transport byte[] data to UDP Socket.
     * @param bytes
     */
    private void udp(byte[] bytes, InetAddress address, int port) throws Exception{
        DatagramPacket packet = new DatagramPacket(bytes, bytes.length, address, port);
        clientSocket.send(packet);
    }


    // Kafka Listeners ///////////////////////////////////////////////////////////////////


    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"0"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume00(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) throws Exception {
    	task(record);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"1"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume01(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) throws Exception {
    	task(record);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"2"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume02(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) throws Exception {
    	task(record);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"3"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume03(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) throws Exception {
    	task(record);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"4"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume04(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) throws Exception {
    	task(record);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"5"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume05(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) throws Exception {
    	task(record);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"6"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume06(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) throws Exception {
    	task(record);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"7"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume07(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) throws Exception {
    	task(record);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"8"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume08(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) throws Exception {
    	task(record);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"9"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume09(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) throws Exception {
    	task(record);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"10"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume10(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) throws Exception {
    	task(record);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"11"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume11(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) throws Exception {
    	task(record);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"12"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume12(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) throws Exception {
    	task(record);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"13"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume13(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) throws Exception {
    	task(record);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"14"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume14(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) throws Exception {
    	task(record);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"15"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume15(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) throws Exception {
    	task(record);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"16"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume16(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) throws Exception {
    	task(record);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"17"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume17(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) throws Exception {
    	task(record);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"18"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume18(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) throws Exception {
    	task(record);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"19"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume19(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) throws Exception {
    	task(record);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"20"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume20(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) throws Exception {
    	task(record);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"21"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume21(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) throws Exception {
    	task(record);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"22"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume22(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) throws Exception {
    	task(record);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"23"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume23(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) throws Exception {
    	task(record);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"24"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume24(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) throws Exception {
    	task(record);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"25"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume25(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) throws Exception {
    	task(record);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"26"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume26(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) throws Exception {
    	task(record);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"27"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume27(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) throws Exception {
    	task(record);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"28"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume28(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) throws Exception {
    	task(record);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"29"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume29(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) throws Exception {
    	task(record);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"30"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume30(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) throws Exception {
    	task(record);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"31"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume31(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) throws Exception {
    	task(record);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"32"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume32(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) throws Exception {
    	task(record);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"33"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume33(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) throws Exception {
    	task(record);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"34"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume34(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) throws Exception {
    	task(record);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"35"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume35(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) throws Exception {
    	task(record);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"36"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume36(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) throws Exception {
    	task(record);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"37"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume37(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) throws Exception {
    	task(record);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"38"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume38(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) throws Exception {
    	task(record);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"39"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume39(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) throws Exception {
    	task(record);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"40"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume40(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) throws Exception {
    	task(record);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"41"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume41(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) throws Exception {
    	task(record);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"42"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume42(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) throws Exception {
    	task(record);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"43"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume43(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) throws Exception {
    	task(record);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"44"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume44(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) throws Exception {
    	task(record);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"45"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume45(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) throws Exception {
    	task(record);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"46"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume46(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) throws Exception {
    	task(record);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"47"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume47(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) throws Exception {
    	task(record);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"48"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume48(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) throws Exception {
    	task(record);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"49"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume49(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) throws Exception {
    	task(record);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"50"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume50(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) throws Exception {
    	task(record);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"51"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume51(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) throws Exception {
    	task(record);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"52"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume52(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) throws Exception {
    	task(record);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"53"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume53(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) throws Exception {
    	task(record);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"54"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume54(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) throws Exception {
    	task(record);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"55"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume55(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) throws Exception {
    	task(record);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"56"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume56(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) throws Exception {
    	task(record);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"57"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume57(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) throws Exception {
    	task(record);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"58"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume58(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) throws Exception {
    	task(record);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"59"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume59(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) throws Exception {
    	task(record);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"60"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume60(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) throws Exception {
    	task(record);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"61"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume61(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) throws Exception {
    	task(record);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"62"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume62(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) throws Exception {
    	task(record);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"63"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume63(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) throws Exception {
    	task(record);
    }
	
}
