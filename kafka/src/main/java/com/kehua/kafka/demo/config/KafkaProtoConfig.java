package com.kehua.kafka.demo.config;

import com.kehua.kafka.demo.serializer.ProtoStuffSerializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.ByteArrayDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.ContainerProperties;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Configuration
@EnableConfigurationProperties({KafkaProperties.class})
public class KafkaProtoConfig {

    private final KafkaProperties properties;

    public KafkaProtoConfig(KafkaProperties properties) {
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
        factory.setValueSerializer(new ProtoStuffSerializer<>());
        return factory;
    }


//    @Bean(name = "realtimeDataConsumerFactory")
//    ConsumerFactory<String, ProductInfo> realtimeDataConsumerFactory() {
//        Map<String, Object> configProperties = this.properties.buildConsumerProperties();
//        List<String> interceptors = new ArrayList<>();
//        configProperties.put(ConsumerConfig.INTERCEPTOR_CLASSES_CONFIG, interceptors);
//
//        DefaultKafkaConsumerFactory<String, ProductInfo> factory =
//                new DefaultKafkaConsumerFactory<>(configProperties);
//        factory.setKeyDeserializer(new StringDeserializer());
//
//        ProtoStuffDeserializer<ProductInfo> valueDeserializer =
//                new ProtoStuffDeserializer<>(ProductInfo.class);
//        factory.setValueDeserializer(valueDeserializer);
//        return factory;
//    }
//
//
//    @Bean(name = "realtimeDataContainerFactory")
//    ConcurrentKafkaListenerContainerFactory<String, ProductInfo> realtimeDataContainerFactory(
//            ConsumerFactory<String, ProductInfo> realtimeDataConsumerFactory) {
//        ConcurrentKafkaListenerContainerFactory<String, ProductInfo> factory =
//                new ConcurrentKafkaListenerContainerFactory<>();
//        factory.setConsumerFactory(realtimeDataConsumerFactory);
//        factory.setConcurrency(4);
//        factory.setBatchListener(true);
//        factory.getContainerProperties().setPollTimeout(1500);
//        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
//        return factory;
//    }



    @Bean(name = "realtimeDataConsumerFactory")
    ConsumerFactory<String, byte[]> realtimeDataConsumerFactory() {
        Map<String, Object> configProperties = this.properties.buildConsumerProperties();
        List<String> interceptors = new ArrayList<>();
        configProperties.put(ConsumerConfig.INTERCEPTOR_CLASSES_CONFIG, interceptors);

        DefaultKafkaConsumerFactory<String, byte[]> factory =
                new DefaultKafkaConsumerFactory<>(configProperties);
        factory.setKeyDeserializer(new StringDeserializer());

//        ProtoStuffDeserializer<byte[]> valueDeserializer =
//                new ProtoStuffDeserializer<>(byte[].class);
        factory.setValueDeserializer(new ByteArrayDeserializer());
        return factory;
    }


    @Bean(name = "realtimeDataContainerFactory")
    ConcurrentKafkaListenerContainerFactory<String, byte[]> realtimeDataContainerFactory(
            ConsumerFactory<String, byte[]> realtimeDataConsumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, byte[]> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(realtimeDataConsumerFactory);
        factory.setConcurrency(4);
        factory.setBatchListener(true);
        factory.getContainerProperties().setPollTimeout(1500);
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
        return factory;
    }
}
