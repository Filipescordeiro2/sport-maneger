package br.com.sportmanager.register.mapper;

import br.com.sportmanager.register.document.SubscritpionDocument;
import br.com.sportmanager.register.dto.response.SubscriptionResponse;
import org.springframework.stereotype.Component;

@Component
public class SubscriptionMapper {

    public static SubscriptionResponse toSubscriptionResponse(SubscritpionDocument subscription) {
        if (subscription == null) return null;
        return SubscriptionResponse
                .builder()
                .subscriptionId(subscription.getSubscriptionId())
                .clientId(subscription.getClientId())
                .plan(subscription.getPlan())
                .status(subscription.getStatus())
                .startDate(subscription.getStartDate())
                .endDate(subscription.getEndDate())
                .createdAt(subscription.getCreatedAt())
                .updatedAt(subscription.getUpdatedAt())
                .build();
    }

    public static SubscritpionDocument toSubscritpionDocument(SubscriptionResponse response) {
        if (response == null) return null;
        return SubscritpionDocument
                .builder()
                .subscriptionId(response.subscriptionId())
                .clientId(response.clientId())
                .plan(response.plan())
                .status(response.status())
                .startDate(response.startDate())
                .endDate(response.endDate())
                .createdAt(response.createdAt())
                .updatedAt(response.updatedAt())
                .build();
    }


}
