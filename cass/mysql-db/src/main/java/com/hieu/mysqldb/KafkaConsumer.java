package com.hieu.mysqldb;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumer.class);
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserActivityRepository userActivityRepository;

    @KafkaListener(topics = "${spring.kafka.consumer.topic.activity}",groupId = "g2")
    public void receiveProduct(ConsumerRecord<String, String> consumerRecord) throws JsonProcessingException {
        LOGGER.info("Received topic={}, key={}, Activity={}", consumerRecord.topic(), consumerRecord.key(), consumerRecord.value());
        UserActivity userActivity = objectMapper.readValue( consumerRecord.value(), UserActivity.class);
        userActivityRepository.save(userActivity);
    }
}
