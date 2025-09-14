package br.com.sportmanager.register.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

@Builder
public record ClientRegisterRequest(
        @NotBlank(message = "name is required")
        String name,

        @NotBlank(message = "cpf is required")
        @CPF(message = "cpf is invalid")
        String cpf,

        @NotBlank(message = "email is required")
        @Email(message = "email is invalid")
        String email,

        @NotNull(message = "phone is required")
        String phone,

        @NotNull(message = "birthday is required")
        LocalDate birthday,

        @Valid
        @NotNull(message = "address is required")
        AddressRequest address ) {
}
