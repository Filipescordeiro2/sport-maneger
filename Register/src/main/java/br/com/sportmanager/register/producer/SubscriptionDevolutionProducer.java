package br.com.sportmanager.register.producer;

import br.com.sportmanager.register.dto.response.ClientResponse;
import br.com.sportmanager.register.exception.ClientException;
import br.com.sportmanager.register.exception.ErrorResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;


@Component
@RequiredArgsConstructor
@Slf4j
public class SubscriptionDevolutionProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    private static final String TOPIC = "subscription-devolution";

    public void sendSuccess(ClientResponse response) {
        try {
            String jsonMessage = objectMapper.writeValueAsString(response);
            kafkaTemplate.send(TOPIC, jsonMessage);
            log.info("[SubscriptionDevolutionProducer - sendSuccess]Message sent to topic {}: {}", TOPIC, jsonMessage);
        } catch (JsonProcessingException e) {
            log.error("[SubscriptionDevolutionProducer - sendSuccess] Failed to convert message to JSON", e);
            throw new RuntimeException("Error converting message to JSON", e);
        }
    }

    public void sendFail(ClientException e) {
        try {
            var errorResponse = new ErrorResponse(
                    400,
                    "Business Error",
                    LocalDateTime.now(),
                    List.of(e.getMessage())
            );
            String jsonMessage = objectMapper.writeValueAsString(errorResponse);
            kafkaTemplate.send(TOPIC, jsonMessage);
            log.info("[SubscriptionDevolutionProducer - sendFail] Message sent to topic {}: {}", TOPIC, jsonMessage);
        } catch (JsonProcessingException ex) {
            log.error("[SubscriptionDevolutionProducer - sendFail] Failed to convert message to JSON", ex);
            throw new RuntimeException("Error converting message to JSON", ex);
        }
    }
}
