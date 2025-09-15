package br.com.sportmanger.subscription.producer;

import br.com.sportmanger.subscription.dto.response.SubscriptionResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class SubscriptionProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    private static final String TOPIC = "subscription-create";

    public void send(SubscriptionResponse response) {
        try {
            String jsonMessage = objectMapper.writeValueAsString(response);
            kafkaTemplate.send(TOPIC, jsonMessage);
            log.info("Message sent to topic {}: {}", TOPIC, jsonMessage);
        } catch (JsonProcessingException e) {
            log.error("Failed to convert SubscriptionResponse to JSON", e);
            throw new RuntimeException("Error converting SubscriptionResponse to JSON", e);
        }
    }
}
