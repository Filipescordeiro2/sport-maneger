package br.com.sportmanger.subscription.kafka.topic;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic subscriptionCreateTopic() {
        return TopicBuilder.name("subscription-create")
                .partitions(3) // pode ajustar conforme necessidade
                .replicas(1)   // geralmente 1 no local
                .build();
    }
}
