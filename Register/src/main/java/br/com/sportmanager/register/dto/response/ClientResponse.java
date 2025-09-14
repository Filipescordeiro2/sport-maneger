package br.com.sportmanager.register.dto.response;

import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
public record ClientResponse(String clientId,
                             String name,
                             String cpf,
                             String email,
                             String phone,
                             Integer age,
                             LocalDate birthday,
                             AddressResponse address,
                             LocalDateTime createdAt,
                             LocalDateTime updatedAt,
                             Boolean active) {
}
