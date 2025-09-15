package br.com.sportmanger.subscription.service;

import br.com.sportmanger.subscription.dto.response.ClientResponse;
import br.com.sportmanger.subscription.dto.request.SubscriptionRequest;
import br.com.sportmanger.subscription.dto.response.SubscriptionResponse;
import br.com.sportmanger.subscription.dto.response.ErrorResponse;
import br.com.sportmanger.subscription.mapper.SubscriptionMapper;
import br.com.sportmanger.subscription.producer.SubscriptionProducer;
import br.com.sportmanger.subscription.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final SubscriptionProducer subscriptionProducer;

    // Criação da subscription e envio para Kafka
    public SubscriptionResponse createSubscription(SubscriptionRequest subscriptionRequest) {
        log.info("[SubscriptionService] Creating subscription for request: {}", subscriptionRequest);

        var subscription = SubscriptionMapper.toSubscriptionDocument(subscriptionRequest);
        subscriptionRepository.save(subscription);

        log.info("[SubscriptionService] Subscription saved: {}", subscription);

        var response = SubscriptionMapper.toSubscriptionResponse(subscription);
        subscriptionProducer.send(response); // envia para o tópico subscription-devolution
        log.info("[SubscriptionService] Message sent to Kafka for subscriptionId: {}", subscription.getSubscriptionId());

        return response;
    }

    // Processa mensagem de sucesso do tópico
    public void handleSubscriptionSuccess(ClientResponse clientResponse) {
        log.info("[SubscriptionService] Handling successful subscription for clientId: {}", clientResponse.clientId());
        // Aqui você poderia atualizar a subscription com dados do clientResponse, se necessário
    }

    // Processa fail do tópico e deleta subscription previamente salva
    public void handleSubscriptionFail(ErrorResponse errorResponse) {
        log.warn("[SubscriptionService] Handling failed subscription: {}", errorResponse.errors());

        errorResponse.errors().forEach(error -> {
            String clientId = extractClientId(error);
            if (clientId != null) {
                subscriptionRepository.findById(clientId).ifPresent(subscription -> {
                    subscriptionRepository.delete(subscription);
                    log.info("[SubscriptionService] Deleted subscription for clientId: {}", clientId);
                });
            }
        });
    }

    // Extrai clientId da mensagem de erro
    private String extractClientId(String errorMessage) {
        if (errorMessage.contains("ClientId: ")) {
            return errorMessage.substring(errorMessage.indexOf("ClientId: ") + 10).trim();
        }
        return null;
    }
}
