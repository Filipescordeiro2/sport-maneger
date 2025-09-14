package br.com.sportmanager.register.kafka.topic;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;


@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic subscriptionDevolutionTopic() {
        return TopicBuilder.name("subscription-devolution")
                .partitions(3) // pode ajustar conforme necessidade
                .replicas(1)   // geralmente 1 no local
                .build();
    }
}
