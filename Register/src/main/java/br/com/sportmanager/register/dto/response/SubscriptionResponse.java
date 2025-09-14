package br.com.sportmanager.register.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record SubscriptionResponse(String subscriptionId,
                                   String clientId,
                                   String plan,
                                   String status,
                                   LocalDateTime startDate,
                                   LocalDateTime endDate,
                                   LocalDateTime createdAt,
                                   LocalDateTime updatedAt) {
}
