package br.com.sportmanager.register.service;

import br.com.sportmanager.register.dto.request.ClientRegisterRequest;
import br.com.sportmanager.register.dto.response.ClientRegisterResponse;
import br.com.sportmanager.register.dto.response.ClientResponse;
import br.com.sportmanager.register.exception.ClientException;
import br.com.sportmanager.register.mapper.ClientMapper;
import br.com.sportmanager.register.repository.ClientRepository;
import br.com.sportmanager.register.validation.ClientValidation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;
    private final ClientValidation clientValidation;

    public ClientRegisterResponse registerClient(ClientRegisterRequest request) {
        log.info("[ClientService - RegisterClient] - Initializing service registerClient");
        try {
            clientValidation.validatePreRegister(request);

            log.info("[ClientService - RegisterClient] - Request: {}", request);
            var client = ClientMapper.toClientDocument(request);

            log.info("[ClientService - RegisterClient] - Translate Request x Document: {}", client);
            clientRepository.save(client);

            var response = ClientMapper.toClientRegisterResponse("Client registered successfully");
            log.info("[ClientService - RegisterClient] - Response: {}", response);

            return response;
        } catch (ClientException e) {
            log.error("[ClientService - RegisterClient] - ClientException: {}", e.getMessage());
            throw new ClientException(e.getMessage());
        } catch (Exception e) {
            log.error("[ClientService - RegisterClient] - Exception: {}", e.getMessage());
            throw e;
        }
    }

    public ClientResponse getClient(String cpf) {
        log.info("[ClientService - getClient] - Initializing service getClient");
        try {
            log.info("[ClientService - getClient] - CPF: {}", cpf);
            var client = clientRepository
                    .findByCpf(cpf)
                    .orElseThrow(()->new ClientException("Client not found for this CPF: " + cpf));

            var clientResponse = ClientMapper.toClientResponse(client);
            log.info("[ClientService - getClient] - Translate Document x Response: {}", clientResponse);

            return clientResponse;
        } catch (ClientException e) {
            log.error("[ClientService - getClient] - ClientException: {}", e.getMessage());
            throw new ClientException(e.getMessage());
        } catch (Exception e) {
            log.error("[ClientService - getClient] - Exception: {}", e.getMessage());
            throw e;
        }
    }
}
