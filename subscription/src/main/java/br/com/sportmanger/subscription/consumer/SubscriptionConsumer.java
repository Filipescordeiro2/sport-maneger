package br.com.sportmanger.subscription.consumer;

import br.com.sportmanger.subscription.dto.response.ClientResponse;
import br.com.sportmanger.subscription.dto.response.ErrorResponse;
import br.com.sportmanger.subscription.service.SubscriptionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class SubscriptionConsumer {

    private final SubscriptionService subscriptionService;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "subscription-devolution", groupId = "subscription-service-group")
    public void consume(String message) {
        try {
            subscriptionService.handleSuccess(objectMapper.readValue(message, ClientResponse.class));
            log.info("[Consumer] Success processed");
        } catch (Exception e1) {
            try {
                subscriptionService.handleFail(objectMapper.readValue(message, ErrorResponse.class));
                log.info("[Consumer] Fail processed");
            } catch (Exception e2) {
                log.error("[Consumer] Failed to process message: {}", message, e2);
            }
        }
    }
}

