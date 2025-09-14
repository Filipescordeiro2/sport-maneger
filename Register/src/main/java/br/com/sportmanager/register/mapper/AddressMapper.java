package br.com.sportmanager.register.mapper;

import br.com.sportmanager.register.document.AddressDocument;
import br.com.sportmanager.register.dto.request.AddressRequest;
import br.com.sportmanager.register.dto.response.AddressResponse;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {

    public static AddressDocument toAddress(AddressRequest request) {
        if (request == null) return null;
        return AddressDocument.builder()
                .street(request.street())
                .number(request.number())
                .complement(request.complement())
                .neighborhood(request.neighborhood())
                .city(request.city())
                .state(request.state())
                .zipCode(request.zipCode())
                .build();
    }

    public static AddressResponse toAddressResponse(AddressDocument address) {
        if (address == null) return null;
        return new AddressResponse(
                address.getStreet(),
                address.getNumber(),
                address.getComplement(),
                address.getNeighborhood(),
                address.getCity(),
                address.getState(),
                address.getZipCode()
        );
    }
}
