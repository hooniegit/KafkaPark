package com.hooniegit.KafkaConsumer.Consumer;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.hooniegit.KafkaConsumer.MSSQL.TagReference;
import com.hooniegit.KafkaConsumer.Netty.NettyChannelClient;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.listener.ConsumerSeekAware;
import org.springframework.stereotype.Service;

import com.hooniegit.KafkaConsumer.MSSQL.StateReference;

// Nexus Dependencies
import com.hooniegit.SourceData.Source.Data;
import com.hooniegit.SourceData.Source.Body;
import com.hooniegit.SourceData.Interface.Package;
import com.hooniegit.SourceData.Interface.TagData;
import com.hooniegit.Xerializer.Kryo.KryoSerializer;

/**
 * Default Kafka Consumer Service.
 * - 64 Partition Structure
 * - De-Serialize ConsumerRecords Value
 * - Re-Factor Data Structure
 * - Transport to Micro Services based on UDP Transportation
 */
@Service
public class DefaultConsumerService implements ConsumerSeekAware {

    private DatagramSocket[] clientSocket = new DatagramSocket[64];
    private final InetAddress[] addresses = new InetAddress[4];
    private boolean initialized = false;

    @Autowired
    private NettyChannelClient nettyChannelClient;

    @Autowired
    private StateReference stateReference;

    @Autowired
    private TagReference tagReference;


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
    private void task(ConsumerRecord<String, byte[]> record, int index) throws Exception {
        // Set-Up Socket
        if (!initialized) { setup(); }

        // De-Serialize
        Data<List<Body>> c = KryoSerializer.deserialize(record.value());

        // Re-Factor
        Package pkg = generatePackage(c.getBody(), c.getHeader().get("timestamp").toString());

//        this.nettyChannelClient.init_tag();
        this.nettyChannelClient.init_mode();
//        this.nettyChannelClient.init_statusOne();
//        this.nettyChannelClient.init_statusTwo();
//        this.nettyChannelClient.init_statusThree();

//        this.nettyChannelClient.sendData(pkg.getValue());
        this.nettyChannelClient.sendMode(pkg.getMode());
//        this.nettyChannelClient.sendStatusOne(pkg.getState());
//        this.nettyChannelClient.sendStatusTwo(pkg.getStatusOne());
//        this.nettyChannelClient.sendStatusThree(pkg.getStatusTwo());

        System.out.println(c.getHeader().get("timestamp"));
    }

    /**
     * Set-Up Socket.
     * @throws Exception
     */
    private void setup() throws Exception {
        // Add Socket Address Here
        this.addresses[0] = InetAddress.getByName("localhost");

        // Define Socket
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
            if (tagReference.getIds().containsKey(id)) {
                int tag_index = tagReference.getIds().get(id);
                values.add( new TagData<Double>(tag_index, b.getValue(), timestamp) );

                int group_index = stateReference.getIds().get(id);
                modes.add( new TagData<Boolean>(group_index, b.isMode(), timestamp) );
            }

            int group = b.getGroup();
            // Check If Group Changed
            if (prevGroup != group) {
                // Check If Group Exists
                if (stateReference.getGroups().containsKey(group)) {
                    int group_index = stateReference.getGroups().get(group)[0];
                    states.add( new TagData<Integer>(group_index, b.getState().getValue(), timestamp) );
                    statusOnes.add( new TagData<String>(group_index, stateReference.getGroups().get(group)[1].toString(), timestamp) );
                    statusTwos.add( new TagData<String>(group_index, stateReference.getGroups().get(group)[2].toString(), timestamp) );
                    prevGroup = group;
                }
            }
        }

        // Return New Package Data
        return new Package(
            values, modes, states, statusOnes, statusTwos
        );
    }

    /**
     * Transport byte[] data to UDP Socket.
     * @param bytes
     */
    private void udp(int index, byte[] bytes, InetAddress address, int port) throws Exception{
        DatagramPacket packet = new DatagramPacket(bytes, bytes.length, address, port);
        clientSocket[index].send(packet);
    }


    // Kafka Listeners ///////////////////////////////////////////////////////////////////


    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"0"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume00(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) throws Exception {
    	task(record, 0);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"1"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume01(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) throws Exception {
    	task(record, 1);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"2"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume02(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) throws Exception {
    	task(record, 2);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"3"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume03(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) throws Exception {
    	task(record, 3);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"4"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume04(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) throws Exception {
    	task(record, 4);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"5"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume05(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) throws Exception {
    	task(record, 5);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"6"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume06(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) throws Exception {
    	task(record, 6);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"7"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume07(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) throws Exception {
    	task(record, 7);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"8"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume08(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) throws Exception {
    	task(record, 8);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"9"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume09(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) throws Exception {
    	task(record, 9);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"10"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume10(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) throws Exception {
    	task(record, 10);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"11"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume11(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) throws Exception {
    	task(record, 11);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"12"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume12(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) throws Exception {
    	task(record, 12);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"13"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume13(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) throws Exception {
    	task(record, 13);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"14"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume14(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) throws Exception {
    	task(record, 14);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"15"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume15(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) throws Exception {
    	task(record, 15);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"16"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume16(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) throws Exception {
    	task(record, 16);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"17"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume17(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) throws Exception {
    	task(record, 17);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"18"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume18(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) throws Exception {
    	task(record, 18);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"19"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume19(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) throws Exception {
    	task(record, 19);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"20"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume20(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) throws Exception {
    	task(record, 20);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"21"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume21(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) throws Exception {
    	task(record, 21);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"22"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume22(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) throws Exception {
    	task(record, 22);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"23"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume23(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) throws Exception {
    	task(record, 23);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"24"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume24(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) throws Exception {
    	task(record, 24);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"25"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume25(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) throws Exception {
    	task(record, 25);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"26"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume26(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) throws Exception {
    	task(record, 26);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"27"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume27(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) throws Exception {
    	task(record, 27);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"28"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume28(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) throws Exception {
    	task(record, 28);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"29"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume29(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) throws Exception {
    	task(record, 29);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"30"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume30(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) throws Exception {
    	task(record, 30);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"31"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume31(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) throws Exception {
    	task(record, 31);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"32"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume32(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) throws Exception {
    	task(record, 32);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"33"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume33(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) throws Exception {
    	task(record, 33);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"34"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume34(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) throws Exception {
    	task(record, 34);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"35"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume35(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) throws Exception {
    	task(record, 35);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"36"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume36(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) throws Exception {
    	task(record, 36);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"37"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume37(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) throws Exception {
    	task(record, 37);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"38"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume38(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) throws Exception {
    	task(record, 38);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"39"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume39(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) throws Exception {
    	task(record, 39);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"40"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume40(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) throws Exception {
    	task(record, 40);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"41"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume41(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) throws Exception {
    	task(record, 41);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"42"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume42(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) throws Exception {
    	task(record, 42);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"43"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume43(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) throws Exception {
    	task(record, 43);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"44"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume44(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) throws Exception {
    	task(record, 44);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"45"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume45(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) throws Exception {
    	task(record, 45);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"46"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume46(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) throws Exception {
    	task(record, 46);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"47"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume47(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) throws Exception {
    	task(record, 47);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"48"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume48(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) throws Exception {
    	task(record, 48);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"49"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume49(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) throws Exception {
    	task(record, 49);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"50"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume50(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) throws Exception {
    	task(record, 50);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"51"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume51(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) throws Exception {
    	task(record, 51);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"52"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume52(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) throws Exception {
    	task(record, 52);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"53"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume53(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) throws Exception {
    	task(record, 53);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"54"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume54(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) throws Exception {
    	task(record, 54);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"55"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume55(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) throws Exception {
    	task(record, 55);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"56"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume56(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) throws Exception {
    	task(record, 56);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"57"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume57(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) throws Exception {
    	task(record, 57);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"58"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume58(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) throws Exception {
    	task(record, 58);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"59"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume59(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) throws Exception {
    	task(record, 59);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"60"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume60(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) throws Exception {
    	task(record, 60);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"61"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume61(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) throws Exception {
    	task(record, 61);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"62"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume62(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) throws Exception {
    	task(record, 62);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "WAT", partitions = {"63"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume63(ConsumerRecord<String, byte[]> record, Consumer<?, ?> consumer) throws Exception {
    	task(record, 63);
    }
	
}
