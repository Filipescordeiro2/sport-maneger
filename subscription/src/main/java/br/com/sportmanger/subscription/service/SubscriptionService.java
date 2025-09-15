package br.com.sportmanger.subscription.service;

import br.com.sportmanger.subscription.document.SubscriptionDocument;
import br.com.sportmanger.subscription.dto.request.SubscriptionRequest;
import br.com.sportmanger.subscription.dto.response.ClientResponse;
import br.com.sportmanger.subscription.dto.response.ErrorResponse;
import br.com.sportmanger.subscription.mapper.SubscriptionMapper;
import br.com.sportmanger.subscription.producer.SubscriptionProducer;
import br.com.sportmanger.subscription.producer.SubscriptionUpdateStatusProducer;
import br.com.sportmanger.subscription.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class SubscriptionService {

    private final SubscriptionUpdateStatusProducer subscriptionUpdateStatusProducer;
    private final SubscriptionRepository subscriptionRepository;
    private final SubscriptionProducer subscriptionProducer;

    private final Map<String, CompletableFuture<Object>> futures = new ConcurrentHashMap<>();

    /**
     * Busca e expira todas as subscriptions que já passaram do endDate
     */
    public int expireSubscriptions() {
        List<SubscriptionDocument> subscriptionsToExpire =
                subscriptionRepository.findByEndDateBeforeAndStatus(LocalDateTime.now(), "ACTIVE");

        subscriptionsToExpire.forEach(subscription -> {
            subscription.setStatus("EXPIRED");
            subscription.setUpdatedAt(LocalDateTime.now());
            subscriptionRepository.save(subscription);

            // Mapear para SubscriptionResponse e enviar evento Kafka
            var response = SubscriptionMapper.toSubscriptionResponse(subscription);
            subscriptionUpdateStatusProducer.sendStatusUpdate(response);

            log.info("Subscription {} expirada e evento Kafka enviado.", subscription.getSubscriptionId());
        });

        return subscriptionsToExpire.size();
    }


    public Object createSubscription(SubscriptionRequest request) {
        var subscription = SubscriptionMapper.toSubscriptionDocument(request);

        CompletableFuture<Object> future = new CompletableFuture<>();
        futures.put(subscription.getClientId(), future);

        subscriptionProducer.send(SubscriptionMapper.toSubscriptionResponse(subscription));
        log.info("[SubscriptionService] Sent to Kafka clientId={}", subscription.getClientId());

        try {
            Object result = future.get(5, TimeUnit.SECONDS);

            if (result instanceof ClientResponse client) {
                subscriptionRepository.save(subscription);
                return client;
            } else if (result instanceof ErrorResponse error) {
                return error;
            }

            return ErrorResponse.builder()
                    .status(500)
                    .message("Unexpected response")
                    .timestamp(LocalDateTime.now())
                    .build();

        } catch (Exception e) {
            return ErrorResponse.builder()
                    .status(500)
                    .message("Timeout waiting for Kafka response")
                    .timestamp(LocalDateTime.now())
                    .build();
        } finally {
            futures.remove(subscription.getClientId());
        }
    }

    public void handleSuccess(ClientResponse client) {
        CompletableFuture<Object> future = futures.get(client.clientId());
        if (future != null) future.complete(client);
    }

    public void handleFail(ErrorResponse error) {
        error.errors().forEach(err -> {
            if (err.contains("ClientId: ")) {
                String clientId = err.substring(err.indexOf("ClientId: ") + 10).trim();
                subscriptionRepository.findById(clientId).ifPresent(subscriptionRepository::delete);
                CompletableFuture<Object> future = futures.get(clientId);
                if (future != null) future.complete(error);
            }
        });
    }
}

