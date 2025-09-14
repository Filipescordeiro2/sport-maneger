package br.com.sportmanager.register.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ClientRegisterResponse(String message, LocalDateTime createdAt) {
}
