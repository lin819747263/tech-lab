package com.kehua.kafka.demo.config;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//@Configuration
//@EnableConfigurationProperties({KafkaProperties.class})
public class KafkaConfig {

    private final KafkaProperties properties;

    public KafkaConfig(KafkaProperties properties) {
        this.properties = properties;
    }


    @Bean(name = "kafkaTemplate")
    public KafkaTemplate<String, Object> kafkaTemplate(ProducerFactory<String, Object> kafkaProducerFactory) {
        KafkaTemplate<String, Object> kafkaTemplate = new KafkaTemplate<>(kafkaProducerFactory);
        kafkaTemplate.setDefaultTopic("666");
        return kafkaTemplate;
    }

    @Bean(name = "kafkaProduceFactory")
    public ProducerFactory<String, Object> kafkaProducerFactory() {
        Map<String, Object> configProperties = this.properties.buildProducerProperties();
        List<String> interceptors = new ArrayList<>();
        configProperties.put(ProducerConfig.INTERCEPTOR_CLASSES_CONFIG, interceptors);

        DefaultKafkaProducerFactory<String, Object> factory =
                new DefaultKafkaProducerFactory<>(configProperties);
        factory.setKeySerializer(new StringSerializer());
        factory.setValueSerializer(new JsonSerializer<>());
        return factory;
    }
}
