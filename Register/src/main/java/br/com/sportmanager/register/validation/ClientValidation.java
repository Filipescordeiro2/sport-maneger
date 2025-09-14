package br.com.sportmanager.register.validation;


import br.com.sportmanager.register.dto.request.ClientRegisterRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class ClientValidation {

    private final List<ValidationStrategy<ClientRegisterRequest>> strategies;

    public void validatePreRegister(ClientRegisterRequest request) {
        log.info("[ClientValidation - validatePreRegister] - Initializing validation validatePreRegister");
        for (ValidationStrategy<ClientRegisterRequest> strategy : strategies) {
            strategy.validate(request);
        }
    }
}
