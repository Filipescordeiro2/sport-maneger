package br.com.sportmanager.register.repository;

import br.com.sportmanager.register.document.ClientDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ClientRepository extends MongoRepository<ClientDocument, String> {
    boolean existsByCpf(String cpf);
    Optional<ClientDocument> findByCpf(String cpf);
}
