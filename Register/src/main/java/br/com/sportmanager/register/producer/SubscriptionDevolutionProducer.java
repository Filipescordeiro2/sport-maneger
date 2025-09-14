package br.com.sportmanager.register.producer;

import br.com.sportmanager.register.dto.response.SubscriptionResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class SubscriptionDevolutionProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    private static final String TOPIC = "subscription-devolution";

    public void send(SubscriptionResponse response, String reason) {
        try {
            // Prepare a JSON payload with the subscription data + reason
            Map<String, Object> message = new HashMap<>();
            message.put("subscription", response);
            message.put("reason", reason);

            String jsonMessage = objectMapper.writeValueAsString(message);

            kafkaTemplate.send(TOPIC, jsonMessage);
            log.info("[SubscriptionDevolutionProducer] Message sent to topic {}: {}", TOPIC, jsonMessage);

        } catch (JsonProcessingException e) {
            log.error("[SubscriptionDevolutionProducer] Failed to convert message to JSON", e);
            throw new RuntimeException("Error converting message to JSON", e);
        }
    }
}
