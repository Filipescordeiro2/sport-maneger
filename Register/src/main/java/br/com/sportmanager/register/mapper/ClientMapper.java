package br.com.sportmanager.register.mapper;

import br.com.sportmanager.register.document.ClientDocument;
import br.com.sportmanager.register.dto.request.ClientRegisterRequest;
import br.com.sportmanager.register.dto.response.ClientRegisterResponse;
import br.com.sportmanager.register.dto.response.ClientResponse;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class ClientMapper {

    public static ClientDocument toClientDocument(final ClientRegisterRequest request) {
        return ClientDocument
                .builder()
                .clientId(UUID.randomUUID().toString())
                .cpf(request.cpf())
                .email(request.email())
                .name(request.name())
                .phone(request.phone())
                .active(true)
                .birthday(request.birthday())
                .age(LocalDate.now().getYear() - request.birthday().getYear())
                .address(AddressMapper.toAddress(request.address()))
                .subscritpion(null)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public static ClientRegisterResponse toClientRegisterResponse(final String message) {
        return ClientRegisterResponse
                .builder()
                .message(message)
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static ClientResponse toClientResponse(final ClientDocument client) {
        return ClientResponse
                .builder()
                .clientId(client.getClientId())
                .cpf(client.getCpf())
                .email(client.getEmail())
                .name(client.getName())
                .phone(client.getPhone())
                .active(client.getActive())
                .birthday(client.getBirthday())
                .age(client.getAge())
                .address(AddressMapper.toAddressResponse(client.getAddress()))
                .subscription(SubscriptionMapper.toSubscriptionResponse(client.getSubscritpion()))
                .createdAt(client.getCreatedAt())
                .updatedAt(client.getUpdatedAt())
                .build();
    }
}
