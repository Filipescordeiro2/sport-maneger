package br.com.sportmanger.subscription.repository;

import br.com.sportmanger.subscription.document.SubscriptionDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SubscriptionRepository extends MongoRepository<SubscriptionDocument, String> {
}
