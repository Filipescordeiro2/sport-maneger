package br.com.sportmanager.register.dto.response;

public record AddressResponse (
        String street,
        String number,
        String complement,
        String neighborhood,
        String city,
        String state,
        String zipCode
) {
}
