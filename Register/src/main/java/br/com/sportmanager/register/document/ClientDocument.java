package br.com.sportmanager.register.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "db_client")
public class ClientDocument {

    @Id
    private String clientId;

    private String name;
    private String cpf;
    private String email;
    private String phone;
    private Integer age;
    private LocalDate birthday;
    private AddressDocument address;
    private SubscritpionDocument subscritpion;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean active;

}
