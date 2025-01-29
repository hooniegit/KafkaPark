package com.hooniegit.KafkaConsumer.Consumer;

import java.util.Collection;
import java.util.List;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.listener.ConsumerAwareRebalanceListener;
import org.springframework.stereotype.Service;

import com.hooniegit.KafkaConsumer.Stream.Stream;
import com.hooniegit.KafkaConsumer.Stream.StreamManager;

@Service
public class KafkaConsumerService implements ConsumerAwareRebalanceListener {

	private final StreamManager<ConsumerRecord<String, byte[]>> manager;
	
    @Autowired
    public KafkaConsumerService(StreamManager<ConsumerRecord<String, byte[]>> manager) {
        this.manager = manager;
    }

    /**
     * 파티션이 새로 등록되었을 때, 해당 파티션의 오프셋 정보를 초기화합니다.
     */
    @Override
    public void onPartitionsAssigned(Consumer<?, ?> consumer, Collection<org.apache.kafka.common.TopicPartition> partitions) {
        partitions.forEach(partition -> {
            consumer.seekToEnd(List.of(new org.apache.kafka.common.TopicPartition(partition.topic(), partition.partition())));
        });
    }

    /**
     * Consumer Task : Publish Event
     * @param record
     */
    private void task(ConsumerRecord<String, byte[]> record) {
        Stream<ConsumerRecord<String, byte[]>> stream = manager.getNextStream();
        stream.publishInitialEvent(record);
    }

    /**
     * Consumer Threads For Each Partition
     * @param records
     * @param consumer
     */
    //

    @KafkaListener(topicPartitions = @TopicPartition(topic = "sample", partitions = {"0"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume00(ConsumerRecords<String, byte[]> records, Consumer<?, ?> consumer) {
    	
    	for (ConsumerRecord<String, byte[]> record : records) {
    		task(record);
    	}
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "sample", partitions = {"1"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume01(ConsumerRecords<String, byte[]> records, Consumer<?, ?> consumer) {
    	
    	for (ConsumerRecord<String, byte[]> record : records) {
    		task(record);
    	}
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "sample", partitions = {"2"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume02(ConsumerRecords<String, byte[]> records, Consumer<?, ?> consumer) {
    	
    	for (ConsumerRecord<String, byte[]> record : records) {
    		task(record);
    	}
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "sample", partitions = {"3"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume03(ConsumerRecords<String, byte[]> records, Consumer<?, ?> consumer) {
    	
    	for (ConsumerRecord<String, byte[]> record : records) {
    		task(record);
    	}
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "sample", partitions = {"4"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume04(ConsumerRecords<String, byte[]> records, Consumer<?, ?> consumer) {
    	
    	for (ConsumerRecord<String, byte[]> record : records) {
    		task(record);
    	}
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "sample", partitions = {"5"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume05(ConsumerRecords<String, byte[]> records, Consumer<?, ?> consumer) {
    	
    	for (ConsumerRecord<String, byte[]> record : records) {
    		task(record);
    	}
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "sample", partitions = {"6"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume06(ConsumerRecords<String, byte[]> records, Consumer<?, ?> consumer) {
    	
    	for (ConsumerRecord<String, byte[]> record : records) {
    		task(record);
    	}
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "sample", partitions = {"7"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume07(ConsumerRecords<String, byte[]> records, Consumer<?, ?> consumer) {
    	
    	for (ConsumerRecord<String, byte[]> record : records) {
    		task(record);
    	}
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "sample", partitions = {"8"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume08(ConsumerRecords<String, byte[]> records, Consumer<?, ?> consumer) {
    	
    	for (ConsumerRecord<String, byte[]> record : records) {
    		task(record);
    	}
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "sample", partitions = {"9"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume09(ConsumerRecords<String, byte[]> records, Consumer<?, ?> consumer) {
    	
    	for (ConsumerRecord<String, byte[]> record : records) {
    		task(record);
    	}
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "sample", partitions = {"10"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume10(ConsumerRecords<String, byte[]> records, Consumer<?, ?> consumer) {
    	
    	for (ConsumerRecord<String, byte[]> record : records) {
    		task(record);
    	}
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "sample", partitions = {"11"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume11(ConsumerRecords<String, byte[]> records, Consumer<?, ?> consumer) {
    	
    	for (ConsumerRecord<String, byte[]> record : records) {
    		task(record);
    	}
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "sample", partitions = {"12"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume12(ConsumerRecords<String, byte[]> records, Consumer<?, ?> consumer) {
    	
    	for (ConsumerRecord<String, byte[]> record : records) {
    		task(record);
    	}
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "sample", partitions = {"13"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume13(ConsumerRecords<String, byte[]> records, Consumer<?, ?> consumer) {
    	
    	for (ConsumerRecord<String, byte[]> record : records) {
    		task(record);
    	}
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "sample", partitions = {"14"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume14(ConsumerRecords<String, byte[]> records, Consumer<?, ?> consumer) {
    	
    	for (ConsumerRecord<String, byte[]> record : records) {
    		task(record);
    	}
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "sample", partitions = {"15"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume15(ConsumerRecords<String, byte[]> records, Consumer<?, ?> consumer) {
    	
    	for (ConsumerRecord<String, byte[]> record : records) {
    		task(record);
    	}
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "sample", partitions = {"16"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume16(ConsumerRecords<String, byte[]> records, Consumer<?, ?> consumer) {
    	
    	for (ConsumerRecord<String, byte[]> record : records) {
    		task(record);
    	}
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "sample", partitions = {"17"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume17(ConsumerRecords<String, byte[]> records, Consumer<?, ?> consumer) {
    	
    	for (ConsumerRecord<String, byte[]> record : records) {
    		task(record);
    	}
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "sample", partitions = {"18"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume18(ConsumerRecords<String, byte[]> records, Consumer<?, ?> consumer) {
    	
    	for (ConsumerRecord<String, byte[]> record : records) {
    		task(record);
    	}
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "sample", partitions = {"19"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume19(ConsumerRecords<String, byte[]> records, Consumer<?, ?> consumer) {
    	
    	for (ConsumerRecord<String, byte[]> record : records) {
    		task(record);
    	}
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "sample", partitions = {"20"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume20(ConsumerRecords<String, byte[]> records, Consumer<?, ?> consumer) {
    	
    	for (ConsumerRecord<String, byte[]> record : records) {
    		task(record);
    	}
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "sample", partitions = {"21"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume21(ConsumerRecords<String, byte[]> records, Consumer<?, ?> consumer) {
    	
    	for (ConsumerRecord<String, byte[]> record : records) {
    		task(record);
    	}
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "sample", partitions = {"22"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume22(ConsumerRecords<String, byte[]> records, Consumer<?, ?> consumer) {
    	
    	for (ConsumerRecord<String, byte[]> record : records) {
    		task(record);
    	}
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "sample", partitions = {"23"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume23(ConsumerRecords<String, byte[]> records, Consumer<?, ?> consumer) {
    	
    	for (ConsumerRecord<String, byte[]> record : records) {
    		task(record);
    	}
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "sample", partitions = {"24"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume24(ConsumerRecords<String, byte[]> records, Consumer<?, ?> consumer) {
    	
    	for (ConsumerRecord<String, byte[]> record : records) {
    		task(record);
    	}
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "sample", partitions = {"25"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume25(ConsumerRecords<String, byte[]> records, Consumer<?, ?> consumer) {
    	
    	for (ConsumerRecord<String, byte[]> record : records) {
    		task(record);
    	}
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "sample", partitions = {"26"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume26(ConsumerRecords<String, byte[]> records, Consumer<?, ?> consumer) {
    	
    	for (ConsumerRecord<String, byte[]> record : records) {
    		task(record);
    	}
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "sample", partitions = {"27"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume27(ConsumerRecords<String, byte[]> records, Consumer<?, ?> consumer) {
    	
    	for (ConsumerRecord<String, byte[]> record : records) {
    		task(record);
    	}
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "sample", partitions = {"28"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume28(ConsumerRecords<String, byte[]> records, Consumer<?, ?> consumer) {
    	
    	for (ConsumerRecord<String, byte[]> record : records) {
    		task(record);
    	}
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "sample", partitions = {"29"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume29(ConsumerRecords<String, byte[]> records, Consumer<?, ?> consumer) {
    	
    	for (ConsumerRecord<String, byte[]> record : records) {
    		task(record);
    	}
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "sample", partitions = {"30"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume30(ConsumerRecords<String, byte[]> records, Consumer<?, ?> consumer) {
    	
    	for (ConsumerRecord<String, byte[]> record : records) {
    		task(record);
    	}
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "sample", partitions = {"31"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume31(ConsumerRecords<String, byte[]> records, Consumer<?, ?> consumer) {
    	
    	for (ConsumerRecord<String, byte[]> record : records) {
    		task(record);
    	}
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "sample", partitions = {"32"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume32(ConsumerRecords<String, byte[]> records, Consumer<?, ?> consumer) {
    	
    	for (ConsumerRecord<String, byte[]> record : records) {
    		task(record);
    	}
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "sample", partitions = {"33"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume33(ConsumerRecords<String, byte[]> records, Consumer<?, ?> consumer) {
    	
    	for (ConsumerRecord<String, byte[]> record : records) {
    		task(record);
    	}
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "sample", partitions = {"34"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume34(ConsumerRecords<String, byte[]> records, Consumer<?, ?> consumer) {
    	
    	for (ConsumerRecord<String, byte[]> record : records) {
    		task(record);
    	}
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "sample", partitions = {"35"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume35(ConsumerRecords<String, byte[]> records, Consumer<?, ?> consumer) {
    	
    	for (ConsumerRecord<String, byte[]> record : records) {
    		task(record);
    	}
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "sample", partitions = {"36"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume36(ConsumerRecords<String, byte[]> records, Consumer<?, ?> consumer) {
    	
    	for (ConsumerRecord<String, byte[]> record : records) {
    		task(record);
    	}
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "sample", partitions = {"37"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume37(ConsumerRecords<String, byte[]> records, Consumer<?, ?> consumer) {
    	
    	for (ConsumerRecord<String, byte[]> record : records) {
    		task(record);
    	}
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "sample", partitions = {"38"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume38(ConsumerRecords<String, byte[]> records, Consumer<?, ?> consumer) {
    	
    	for (ConsumerRecord<String, byte[]> record : records) {
    		task(record);
    	}
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "sample", partitions = {"39"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume39(ConsumerRecords<String, byte[]> records, Consumer<?, ?> consumer) {
    	
    	for (ConsumerRecord<String, byte[]> record : records) {
    		task(record);
    	}
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "sample", partitions = {"40"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume40(ConsumerRecords<String, byte[]> records, Consumer<?, ?> consumer) {
    	
    	for (ConsumerRecord<String, byte[]> record : records) {
    		task(record);
    	}
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "sample", partitions = {"41"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume41(ConsumerRecords<String, byte[]> records, Consumer<?, ?> consumer) {
    	
    	for (ConsumerRecord<String, byte[]> record : records) {
    		task(record);
    	}
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "sample", partitions = {"42"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume42(ConsumerRecords<String, byte[]> records, Consumer<?, ?> consumer) {
    	
    	for (ConsumerRecord<String, byte[]> record : records) {
    		task(record);
    	}
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "sample", partitions = {"43"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume43(ConsumerRecords<String, byte[]> records, Consumer<?, ?> consumer) {
    	
    	for (ConsumerRecord<String, byte[]> record : records) {
    		task(record);
    	}
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "sample", partitions = {"44"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume44(ConsumerRecords<String, byte[]> records, Consumer<?, ?> consumer) {
    	
    	for (ConsumerRecord<String, byte[]> record : records) {
    		task(record);
    	}
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "sample", partitions = {"45"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume45(ConsumerRecords<String, byte[]> records, Consumer<?, ?> consumer) {
    	
    	for (ConsumerRecord<String, byte[]> record : records) {
    		task(record);
    	}
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "sample", partitions = {"46"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume46(ConsumerRecords<String, byte[]> records, Consumer<?, ?> consumer) {
    	
    	for (ConsumerRecord<String, byte[]> record : records) {
    		task(record);
    	}
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "sample", partitions = {"47"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume47(ConsumerRecords<String, byte[]> records, Consumer<?, ?> consumer) {
    	
    	for (ConsumerRecord<String, byte[]> record : records) {
    		task(record);
    	}
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "sample", partitions = {"48"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume48(ConsumerRecords<String, byte[]> records, Consumer<?, ?> consumer) {
    	
    	for (ConsumerRecord<String, byte[]> record : records) {
    		task(record);
    	}
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "sample", partitions = {"49"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume49(ConsumerRecords<String, byte[]> records, Consumer<?, ?> consumer) {
    	
    	for (ConsumerRecord<String, byte[]> record : records) {
    		task(record);
    	}
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "sample", partitions = {"50"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume50(ConsumerRecords<String, byte[]> records, Consumer<?, ?> consumer) {
    	
    	for (ConsumerRecord<String, byte[]> record : records) {
    		task(record);
    	}
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "sample", partitions = {"51"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume51(ConsumerRecords<String, byte[]> records, Consumer<?, ?> consumer) {
    	
    	for (ConsumerRecord<String, byte[]> record : records) {
    		task(record);
    	}
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "sample", partitions = {"52"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume52(ConsumerRecords<String, byte[]> records, Consumer<?, ?> consumer) {
    	
    	for (ConsumerRecord<String, byte[]> record : records) {
    		task(record);
    	}
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "sample", partitions = {"53"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume53(ConsumerRecords<String, byte[]> records, Consumer<?, ?> consumer) {
    	
    	for (ConsumerRecord<String, byte[]> record : records) {
    		task(record);
    	}
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "sample", partitions = {"54"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume54(ConsumerRecords<String, byte[]> records, Consumer<?, ?> consumer) {
    	
    	for (ConsumerRecord<String, byte[]> record : records) {
    		task(record);
    	}
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "sample", partitions = {"55"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume55(ConsumerRecords<String, byte[]> records, Consumer<?, ?> consumer) {
    	
    	for (ConsumerRecord<String, byte[]> record : records) {
    		task(record);
    	}
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "sample", partitions = {"56"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume56(ConsumerRecords<String, byte[]> records, Consumer<?, ?> consumer) {
    	
    	for (ConsumerRecord<String, byte[]> record : records) {
    		task(record);
    	}
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "sample", partitions = {"57"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume57(ConsumerRecords<String, byte[]> records, Consumer<?, ?> consumer) {
    	
    	for (ConsumerRecord<String, byte[]> record : records) {
    		task(record);
    	}
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "sample", partitions = {"58"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume58(ConsumerRecords<String, byte[]> records, Consumer<?, ?> consumer) {
    	
    	for (ConsumerRecord<String, byte[]> record : records) {
    		task(record);
    	}
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "sample", partitions = {"59"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume59(ConsumerRecords<String, byte[]> records, Consumer<?, ?> consumer) {
    	
    	for (ConsumerRecord<String, byte[]> record : records) {
    		task(record);
    	}
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "sample", partitions = {"60"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume60(ConsumerRecords<String, byte[]> records, Consumer<?, ?> consumer) {
    	
    	for (ConsumerRecord<String, byte[]> record : records) {
    		task(record);
    	}
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "sample", partitions = {"61"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume61(ConsumerRecords<String, byte[]> records, Consumer<?, ?> consumer) {
    	
    	for (ConsumerRecord<String, byte[]> record : records) {
    		task(record);
    	}
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "sample", partitions = {"62"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume62(ConsumerRecords<String, byte[]> records, Consumer<?, ?> consumer) {
    	
    	for (ConsumerRecord<String, byte[]> record : records) {
    		task(record);
    	}
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "sample", partitions = {"63"}), 
                   containerFactory = "kafkaListenerContainerFactory")
    public void consume63(ConsumerRecords<String, byte[]> records, Consumer<?, ?> consumer) {
    	
    	for (ConsumerRecord<String, byte[]> record : records) {
    		task(record);
    	}
    }
	
}
