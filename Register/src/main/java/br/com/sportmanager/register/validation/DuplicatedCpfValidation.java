package br.com.sportmanager.register.validation;

import br.com.sportmanager.register.dto.request.ClientRegisterRequest;
import br.com.sportmanager.register.exception.ClientException;
import br.com.sportmanager.register.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class DuplicatedCpfValidation implements ValidationStrategy<ClientRegisterRequest>{

    private final ClientRepository clientRepository;

    @Override
    public void validate(ClientRegisterRequest request) {
        log.info("[DuplicatedCpfValidation - validate] - Initializing validation DuplicatedCpfValidation");
        if (clientRepository.existsByCpf(request.cpf())){
            throw new ClientException("CPF: "+request.cpf()+" already exists");
        }
        log.info("[DuplicatedCpfValidation - validate] - Validation Ok");
    }
}
