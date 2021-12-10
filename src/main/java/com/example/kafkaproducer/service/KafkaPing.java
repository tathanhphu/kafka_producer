package com.example.kafkaproducer.service;

import com.example.kafkaproducer.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;

@Component
public class KafkaPing {
  private KafkaProducer kafkaProducer;

  public KafkaPing(KafkaProducer kafkaProducer) {
    this.kafkaProducer = kafkaProducer;
  }

  @Value("${kafka.topic.name}")
  private String topicName;

  @Scheduled(cron = "${kafka.healthCheck.interval}")
  public void pingKafka() {
    kafkaProducer.sendMessage(topicName, new Date().toString());
  }

  @PostConstruct
  public void postConstruct() {
    System.out.println("Topic name " + topicName);
  }
}