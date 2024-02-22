package com.kehua.kafka.demo.sender;

import com.kehua.kafka.demo.pojo.ProductInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class KafkaPojoSender {

    @Autowired
    KafkaTemplate<String, Object> kafkaTemplate;

    @Scheduled(fixedRate = 1000 * 30)
    public void send(){
        kafkaTemplate.send("test8", new ProductInfo(1L, "66666"));
    }
}
