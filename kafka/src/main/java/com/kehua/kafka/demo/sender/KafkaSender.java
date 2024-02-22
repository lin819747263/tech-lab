package com.kehua.kafka.demo.sender;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;

@Component
public class KafkaSender {

    @Autowired(required = false)
    KafkaTemplate<String, String> kafkaTemplate;

//    @Scheduled(fixedRate = 1000)
    public void send(){
        kafkaTemplate.send("test", "springboot");
    }

//    @Scheduled(fixedRate = 1000 * 30)
    public void send01(){
        for(int i=0; i< 10; i++){
            kafkaTemplate.send("test10",  i + "", "springboot" + i);
        }
    }

//    @Scheduled(fixedRate = 1000 * 30)
    public void send02(){
        for(int i=0; i< 10; i++){
            int finalI = i;
            kafkaTemplate.send(new Message<Object>() {
                @Override
                public Object getPayload() {
                    return "springboot" + finalI;
                }

                @Override
                public MessageHeaders getHeaders() {
                    return new MessageHeaders(new HashMap<String,Object>(){{
                        put("kafka_topic", "test");
                    }});
                }
            });
        }

    }

//    @Scheduled(fixedRate = 1000 * 30)
    public void send03(){
        ProducerRecord<String, String> record = new ProducerRecord<>("test10", "springboot");
        kafkaTemplate.send(record);
    }


//    @Scheduled(fixedRate = 1000 * 30)
    public void send04() throws ExecutionException, InterruptedException {
        kafkaTemplate.send("test10", "springboot").get();
    }
}
