package br.com.sportmanager.register.controller;

import br.com.sportmanager.register.dto.request.ClientRegisterRequest;
import br.com.sportmanager.register.dto.response.ClientRegisterResponse;
import br.com.sportmanager.register.dto.response.ClientResponse;
import br.com.sportmanager.register.service.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/register")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @PostMapping("/client")
    public ClientRegisterResponse registerClient(@Valid @RequestBody ClientRegisterRequest request) {
        return clientService.registerClient(request);
    }

    @GetMapping("/client/{cpf}")
    public ClientResponse registerClient(@PathVariable String cpf) {
        return clientService.getClient(cpf);
    }

}
