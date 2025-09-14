package br.com.sportmanager.register.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AddressRequest(

        @NotBlank(message = "street is required")
        @Size(max = 100, message = "street must be at most 100 characters")
        String street,

        @NotBlank(message = "number is required")
        @Size(max = 10, message = "number must be at most 10 characters")
        String number,

        @Size(max = 50, message = "complement must be at most 50 characters")
        String complement,

        @NotBlank(message = "neighborhood is required")
        @Size(max = 50, message = "neighborhood must be at most 50 characters")
        String neighborhood,

        @NotBlank(message = "city is required")
        @Size(max = 50, message = "city must be at most 50 characters")
        String city,

        @NotBlank(message = "state is required")
        @Size(max = 2, message = "state must be 2 characters")
        String state,

        @NotBlank(message = "zipCode is required")
        @Size(max = 10, message = "zipCode must be at most 10 characters")
        String zipCode

) {}
