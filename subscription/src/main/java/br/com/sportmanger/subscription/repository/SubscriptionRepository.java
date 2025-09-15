package br.com.sportmanger.subscription.repository;

import br.com.sportmanger.subscription.document.SubscriptionDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface SubscriptionRepository extends MongoRepository<SubscriptionDocument, String> {
    List<SubscriptionDocument> findByEndDateBeforeAndStatus(LocalDateTime endDate, String status);

}
