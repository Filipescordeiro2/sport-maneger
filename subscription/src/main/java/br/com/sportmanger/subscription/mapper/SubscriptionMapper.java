package br.com.sportmanger.subscription.mapper;

import br.com.sportmanger.subscription.document.SubscriptionDocument;
import br.com.sportmanger.subscription.dto.request.SubscriptionRequest;
import br.com.sportmanger.subscription.dto.response.SubscriptionResponse;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class Subscription {

    public static SubscriptionDocument toSubscriptionDocument(SubscriptionRequest request) {
        return SubscriptionDocument
                .builder()
                .subscriptionId(UUID.randomUUID().toString())
                .clientId(request.clientId())
                .plan(request.plan())
                .status(request.status())
                .startDate(request.startDate())
                .endDate(request.endDate())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public static SubscriptionResponse toSubscriptionResponse(SubscriptionDocument document) {
        return SubscriptionResponse
                .builder()
                .subscriptionId(UUID.randomUUID().toString())
                .clientId(document.getClientId())
                .plan(document.getPlan())
                .status(document.getStatus())
                .startDate(document.getStartDate())
                .endDate(document.getEndDate())
                .createdAt(document.getCreatedAt())
                .updatedAt(document.getUpdatedAt())
                .build();
    }
}
