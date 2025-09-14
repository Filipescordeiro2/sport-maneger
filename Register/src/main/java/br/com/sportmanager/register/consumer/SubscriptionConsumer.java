package br.com.sportmanager.register.consumer;

import br.com.sportmanager.register.dto.response.SubscriptionResponse;
import br.com.sportmanager.register.service.SubscriptionHandlerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SubscriptionConsumer {

    private final SubscriptionHandlerService subscriptionHandlerService;
    private final ObjectMapper objectMapper;

    @KafkaListener(
            topics = "subscription-create",
            groupId = "register-service-group",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void consume(ConsumerRecord<String, String> record) {
        try {
            log.info("[SubscriptionConsumer] Received message from topic {}: {}", record.topic(), record.value());
            SubscriptionResponse subscriptionResponse =
                    objectMapper.readValue(record.value(), SubscriptionResponse.class);

            subscriptionHandlerService.handleSubscription(subscriptionResponse);
            log.info("[SubscriptionConsumer] Deserialized subscription: {}", subscriptionResponse);
        } catch (Exception e) {
            log.error("[SubscriptionConsumer] Error processing message: {}", e.getMessage(), e);
        }
    }
}
