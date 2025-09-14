package br.com.sportmanger.subscription.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record SubscriptionRequest(@NotBlank(message = "clientId is required")
                                  String clientId,

                                  @NotBlank(message = "plan is required")
                                  String plan,

                                  @NotBlank(message = "status is required")
                                  String status,

                                  @NotNull(message = "startDate is required")
                                  LocalDateTime startDate,

                                  @NotNull(message = "endDate is required")
                                  LocalDateTime endDate) {
}
