package br.com.sportmanger.subscription.consumer;

import br.com.sportmanger.subscription.dto.response.ClientResponse;
import br.com.sportmanger.subscription.dto.response.ErrorResponse;
import br.com.sportmanger.subscription.service.SubscriptionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class SubscriptionConsumer {

    private final SubscriptionService subscriptionService;
    private final ObjectMapper objectMapper;

    @KafkaListener(
            topics = "subscription-devolution",
            groupId = "subscription-service-group",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void consume(ConsumerRecord<String, String> record) {
        log.info("[SubscriptionConsumer] Received message from topic {}: {}", record.topic(), record.value());

        try {
            // Tenta desserializar como sucesso
            var clientResponse = objectMapper.readValue(record.value(), ClientResponse.class);
            subscriptionService.handleSubscriptionSuccess(clientResponse);
            log.info("[SubscriptionConsumer] Processed success message for clientId: {}", clientResponse.clientId());

        } catch (Exception successException) {
            try {
                // Se falhar, tenta desserializar como erro
                var errorResponse = objectMapper.readValue(record.value(), ErrorResponse.class);
                subscriptionService.handleSubscriptionFail(errorResponse);
                log.info("[SubscriptionConsumer] Processed fail message: {}", errorResponse.errors());

            } catch (Exception errorException) {
                log.error("[SubscriptionConsumer] Failed to process message as success or fail: {}", record.value(), errorException);
            }
        }
    }
}
