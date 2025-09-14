package br.com.sportmanager.register.service;

import br.com.sportmanager.register.dto.response.SubscriptionResponse;
import br.com.sportmanager.register.exception.ClientException;
import br.com.sportmanager.register.mapper.ClientMapper;
import br.com.sportmanager.register.mapper.SubscriptionMapper;
import br.com.sportmanager.register.producer.SubscriptionDevolutionProducer;
import br.com.sportmanager.register.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class SubscriptionHandlerService {

    private final ClientRepository clientRepository;
    private final SubscriptionDevolutionProducer subscriptionDevolutionProducer;

    public void handleSubscription(SubscriptionResponse response) {
        try {
            var clientSubscription = clientRepository.findByClientId(response.clientId())
                    .orElseThrow(()->new ClientException("Client not found for this ClientId: " + response.clientId()));

            clientSubscription.setSubscritpion(SubscriptionMapper.toSubscritpionDocument(response));
            clientSubscription.setUpdatedAt(LocalDateTime.now());
            var client = clientRepository.save(clientSubscription);
            subscriptionDevolutionProducer.sendSuccess(ClientMapper.toClientResponse(client));
        }catch (ClientException e) {
            subscriptionDevolutionProducer.sendFail(new ClientException(e.getMessage()));
        }
    }
}
