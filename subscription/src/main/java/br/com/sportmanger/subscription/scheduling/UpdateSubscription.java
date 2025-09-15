package br.com.sportmanger.subscription.scheduling;

import br.com.sportmanger.subscription.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class UpdateSubscription {

    private final SubscriptionService subscriptionService;

    // Roda a cada minuto para teste
    @Scheduled(cron = "0 * * * * *")
    public void validateSubscriptions() {
        log.info("Iniciando validação das subscriptions expiradas...");
        try {
            int total = subscriptionService.expireSubscriptions();
            log.info("Validação das subscriptions finalizada. Total expiradas: {}", total);
        } catch (Exception e) {
            log.error("Erro ao validar subscriptions", e);
        }
    }
}


