package com.kehua.rk.demo.sender;


import com.kehua.rk.demo.Tops;
import org.apache.rocketmq.client.apis.ClientException;
import org.apache.rocketmq.client.apis.message.Message;
import org.apache.rocketmq.client.apis.producer.Producer;
import org.apache.rocketmq.client.apis.producer.SendReceipt;
import org.apache.rocketmq.client.java.message.MessageBuilderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class Sender {

    @Autowired
    Producer producer;

    @Scheduled(fixedRate = 15000L)
    public void sendNormal() throws IOException {

        // 普通消息发送。
        Message message = new MessageBuilderImpl()
                .setTopic(Tops.normalTopic)
                // 设置消息索引键，可根据关键字精确查找某条消息。
                .setKeys("messageKey")
                // 设置消息Tag，用于消费端根据指定Tag过滤消息。
                .setTag("messageTag")
                // 消息体。
                .setBody("messageBody".getBytes())
                .build();

        try {
            // 发送消息，需要关注发送结果，并捕获失败等异常。
            SendReceipt sendReceipt = producer.send(message);
            System.out.println("Send message successfully, messageId={}" + sendReceipt.getMessageId());
        } catch (ClientException e) {
            e.printStackTrace();
        }
//         producer.close();
    }


    @Scheduled(fixedRate = 15000L)
    public void sendDelay() throws IOException {

        // 普通消息发送。
        Message message = new MessageBuilderImpl()
                .setTopic(Tops.delayTopic)
                // 设置消息索引键，可根据关键字精确查找某条消息。
                .setKeys("messageKey")
                // 设置消息Tag，用于消费端根据指定Tag过滤消息。
                .setTag("messageTag")
                .setDeliveryTimestamp(System.currentTimeMillis() + 3 * 60 * 1000)
                // 消息体。
                .setBody("messageBody".getBytes())
                .build();

        try {
            // 发送消息，需要关注发送结果，并捕获失败等异常。
            SendReceipt sendReceipt = producer.send(message);
            System.out.println("Send message successfully, messageId={}" + sendReceipt.getMessageId());
        } catch (ClientException e) {
            e.printStackTrace();
        }
//         producer.close();
    }
}
