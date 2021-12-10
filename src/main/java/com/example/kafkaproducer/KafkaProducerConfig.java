package com.example.kafkaproducer;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig<K, V> {

  @Value("${kafka.bootstrap.servers}")
  private String bootstrapServers;

  @Value("${kafka.key.serializer:org.apache.kafka.common.serialization.StringSerializer}")
  private String keySerializerConfig;

  @Value("${kafka.value.serializer:org.apache.kafka.common.serialization.StringSerializer}")
  private String valueSerializerConfig;


  @Bean
  public ProducerFactory<K, V> producerFactory() {
    Map<String, Object> configProps = new HashMap<>();
    configProps.put(
        ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
        bootstrapServers);
    configProps.put(
        ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
        keySerializerConfig);
    configProps.put(
        ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
        valueSerializerConfig);
    return new DefaultKafkaProducerFactory<>(configProps);
  }

  @Bean
  @Qualifier("kafkaTemplate")
  public KafkaTemplate<K, V> kafkaTemplate() {
    return new KafkaTemplate<>(producerFactory());
  }
}