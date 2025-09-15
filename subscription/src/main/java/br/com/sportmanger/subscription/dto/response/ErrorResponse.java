package br.com.sportmanger.subscription.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record ErrorResponse(int status,
                            String message,
                            LocalDateTime timestamp,
                            List<String> errors) {
}
