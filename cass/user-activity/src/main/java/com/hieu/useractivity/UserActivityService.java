package com.hieu.useractivity;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Random;
import java.util.UUID;

@Service
public class UserActivityService {
    private final KafkaProducerService kafkaProducerService;
    private final String topic;
    private final JsonMapperWrapper jsonMapper;
    public UserActivityService(KafkaProducerService kafkaProducerService, @Value("${spring.kafka.consumer.topic.user}") String topic, JsonMapperWrapper jsonMapper) {
        this.kafkaProducerService = kafkaProducerService;
        this.topic = topic;
        this.jsonMapper = jsonMapper;
    }

    @Scheduled(fixedRate = 500)
    public void emitUserActivity() {
        UserActivity activity = generateRandomUserActivity();
        kafkaProducerService.send(topic,activity.getActivityType(), jsonMapper.writeValue(activity));
    }

    public UserActivity generateRandomUserActivity() {
        String userId = UUID.randomUUID().toString();
        String details = "Random Details: " + UUID.randomUUID().toString();
        Random random = new Random();
        int randomInteger = random.nextInt(3);
        KeySet[] keySets = KeySet.values();
        String activityType = keySets[randomInteger].name();
        Instant timestamp = Instant.now();
        return new UserActivity(userId, activityType, timestamp, details);
    }
}
