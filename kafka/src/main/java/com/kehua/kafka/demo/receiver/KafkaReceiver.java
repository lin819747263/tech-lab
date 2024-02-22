package com.kehua.kafka.demo.receiver;

import com.kehua.kafka.demo.serializer.ProtoStuffDeserializer;
import com.kehua.kafka.demo.pojo.ProductInfo;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class KafkaReceiver {

//    @KafkaListener(groupId = "g01",topics = "test10")
    public void receive(String msg){
        System.out.println(msg);
    }

//    @KafkaListener(groupId = "g01",
//            topicPartitions = {@TopicPartition(topic = "test10", partitions = {"0"})})
    public void receive2(String msg){
        System.out.println(msg);
    }

    @KafkaListener(groupId = "g01",topics = "test8", containerFactory = "realtimeDataContainerFactory")
    public void receive3(List<ConsumerRecord<String, byte[]>> records, Acknowledgment ack){
        records.forEach(x -> {
            byte[] data = x.value();
            ProtoStuffDeserializer<ProductInfo> deserializer = new ProtoStuffDeserializer<>(ProductInfo.class);
            ProductInfo info = deserializer.deserialize("", data);
            System.out.println(info);
        });
    }
}
