package com.kehua.rk.demo.config;

import com.kehua.rk.demo.Tops;
import org.apache.rocketmq.client.apis.ClientConfiguration;
import org.apache.rocketmq.client.apis.ClientException;
import org.apache.rocketmq.client.apis.ClientServiceProvider;
import org.apache.rocketmq.client.apis.producer.Producer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MqConfig {

    @Bean
    public Producer producer() throws ClientException {
        String endpoint = "192.168.68.252:8081";

        ClientServiceProvider provider = ClientServiceProvider.loadService();
        ClientConfiguration configuration = ClientConfiguration.newBuilder()
                .setEndpoints(endpoint)
                .build();

        return provider.newProducerBuilder()
                .setTopics(Tops.normalTopic, Tops.delayTopic)
                .setClientConfiguration(configuration)
                .build();
    }
}
